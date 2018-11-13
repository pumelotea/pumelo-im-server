package io.pumelo.initializer;

import io.pumelo.ServiceInit;
import io.pumelo.common.basebean.PermissionType;
import io.pumelo.common.entity.RoleTag;
import io.pumelo.data.backend.entity.*;
import io.pumelo.data.backend.repo.*;
import io.pumelo.data.backend.entity.*;
import io.pumelo.data.backend.repo.*;
import io.pumelo.permission.annotation.PermissionRegister;
import io.pumelo.redis.ObjectRedis;
import io.pumelo.service.PermissionService;
import io.pumelo.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 服务启动事件监听器
 */
@Component
public class SystemInit implements ServiceInit {

    @Autowired
    private PermissionEntityRepo permissionEntityRepo;
    @Autowired
    private ObjectRedis redis;
    @Autowired
    private RoleEntityRepo roleEntityRepo;
    @Autowired
    private RolePermissionEntityRepo rolePermissionEntityRepo;
    @Autowired
    private UserEntityRepo userEntityRepo;
    @Autowired
    private UserRoleEntityRepo userRoleEntityRepo;
    @Autowired
    private PermissionService permissionService;

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void appStartedOperate(ApplicationEvent event) {
        this.applicationContext = ((ApplicationReadyEvent) event).getApplicationContext();
        initApplicationBaseData();
    }

    @Override
    public void appClosedOperate(ApplicationEvent event) {
        System.err.println("进程正在关闭");
    }

    /**
     * 初始化应用基础数据
     * 角色，权限，顶级管理员
     */
    @Transactional
    public void initApplicationBaseData() {
        initPermissionData();
        String roleId = initRole();
        initRolePermission(roleId);
        initUser(roleId);
    }

    @Transactional
    public String initRole(){
        RoleEntity superRole = roleEntityRepo.findByCreatedBy("system");
        if (superRole == null) {
            superRole = new RoleEntity();
            superRole.setRoleName("顶级管理员");
            superRole.setRoleDescription("系统自动创建");
            superRole.setRoleTag(RoleTag.ROOT_ROLE);
            superRole.setCreatedBy("system");
            superRole = roleEntityRepo.save(superRole);
            if(superRole != null){
                LOG.info(this,"初始化系统顶级角色成功");
            }else {
                LOG.info(this,"初始化系统顶级角色失败");
            }
        }
        return superRole.getRoleId();
    }

    /**
     * 系统每次启动都会进行重建权限列表
     * @param roleId
     */
    private void initRolePermission(String roleId){
        List<PermissionEntity> allPermissions = permissionEntityRepo.findAll();
        List<RolePermissionEntity> roleMenuEntities = rolePermissionEntityRepo.findByRoleId(roleId);
        rolePermissionEntityRepo.deleteAll(roleMenuEntities);
        List<RolePermissionEntity> rolePermissionEntities = new ArrayList<>();
        allPermissions.forEach(permissionEntity -> rolePermissionEntities.add(new RolePermissionEntity(roleId, permissionEntity.getPermissionKey())));
        List<RolePermissionEntity> save = rolePermissionEntityRepo.saveAll(rolePermissionEntities);
        if(save == null){
            LOG.info(this,"重建顶级角色权限列表失败");
        }
        LOG.info(this,"重建顶级角色权限列表成功");
    }

    /**
     * 自动初始化菜单权限表
     */
    private void initPermissionData(){
        LOG.info(this,"开始初始化菜单权限");
        List<Object[]> list = permissionService.buildMenus();
        List<PermissionEntity> menuEntities = new ArrayList<>();
        list.forEach(objects -> {

            PermissionRegister permissionRegister = (PermissionRegister) objects[1];
            String method = (String)objects[2];
            method = method.toUpperCase();
            String id = permissionRegister.key(),
                    menuName = permissionRegister.name(),
                    parentId = "".equals(permissionRegister.parentKey()) ? null : permissionRegister.parentKey();
            PermissionType menuType = permissionRegister.type();
            String url = permissionRegister.mappingUrl();
            Optional<PermissionEntity> permissionEntity = permissionEntityRepo.findByPermissionKey(id);
            PermissionEntity newPermissonEntity = permissionEntity.orElse(new PermissionEntity(id, menuName, parentId, menuType, url, permissionRegister.sortNum(),method));
            menuEntities.add(newPermissonEntity);
        });
        //获取菜单排序
        menuEntities.stream().filter(permissionEntity -> permissionEntity.getParentKey()!=null).forEach(permissionEntity -> {
            String parent_id = permissionEntity.getParentKey();
            Integer sortNum = permissionEntity.getSortNum();
            while (parent_id != null){
                String temp = parent_id;
                List<PermissionEntity> parent = menuEntities.stream()
                        .filter(permissionEntity1 -> permissionEntity1.getPermissionKey().equals(temp))
                        .limit(1).collect(Collectors.toList());
                if (parent.size()>0){
                    parent_id = parent.get(0).getParentKey();
//                    sortNum = parent.get(0).getSortNum();
                }else {
                    break;
                }
            }
            permissionEntity.setSortNum(sortNum);
        });
        //FIXME 菜单数据要结构化缓存
//        menuEntities.forEach(permissionEntity -> {
//            SessionPermission sessionPermission = permissionEntity.createSessionMenu();
//            //菜单数据
//            redis.add("/menu/"+ permissionEntity.getPermissionKey(), sessionPermission);
//        });
        permissionEntityRepo.saveAll(menuEntities);
    }

    private void initUser(String roleId){
        UserEntity rootUser = userEntityRepo.findByUsernameAndIsTrashFalse("root");
        if (rootUser == null) {
            rootUser = new UserEntity();
            rootUser.createRootUser("root", "123456", "18368404489", "1139559925@qq.com");
            rootUser.setCreatedBy("system");
            UserEntity save = userEntityRepo.save(rootUser);
            if(save.getUserId() != null){
                LOG.info(this,"初始化系统默认帐号成功");
            }else {
                LOG.info(this,"初始化系统默认帐号失败");
            }
        }
        List<UserRoleEntity> userRoleEntityList = userRoleEntityRepo.findByUserIdAndIsTrashFalse(rootUser.getUserId());
        if(userRoleEntityList == null || userRoleEntityList.isEmpty()){
            UserRoleEntity userRoleEntity = new UserRoleEntity(rootUser.getUserId(), roleId);
            UserRoleEntity save = userRoleEntityRepo.save(userRoleEntity);
            if(save != null){
                LOG.info(this,"初始化角色-用户关联成功");
            }else {
                LOG.info(this,"初始化角色-用户关联失败");
            }
        }
    }


}
