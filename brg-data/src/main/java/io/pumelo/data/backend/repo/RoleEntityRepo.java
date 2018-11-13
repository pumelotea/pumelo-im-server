package io.pumelo.data.backend.repo;

import io.pumelo.data.backend.entity.RoleEntity;
import io.pumelo.data.backend.vo.role.RoleInfoVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Fyb
 * on 2017/9/18.
 */
public interface RoleEntityRepo extends JpaRepository<RoleEntity,String> {
    RoleEntity findByCreatedBy(String system);
    RoleEntity findByRoleIdAndIsTrashFalse(String roleId);

    @Query("select new io.pumelo.data.vo.role.RoleInfoVo(r.roleId,r.roleName,r.roleDescription) " +
            "from RoleEntity r where r.isTrash = false")
    Page<RoleInfoVo> findPage(Pageable pageable);


    @Query("select new io.pumelo.data.vo.role.RoleInfoVo(r.roleId,r.roleName,r.roleDescription) " +
            "from RoleEntity r where r.isTrash = false")
    List<RoleInfoVo> findList();
}
