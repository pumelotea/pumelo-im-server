package io.pumelo.im.service;

import io.pumelo.common.errorcode.IMCode;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.entity.UserEntity;
import io.pumelo.data.im.repo.UserEntityRepo;
import io.pumelo.data.im.vo.AccessTokenVo;
import io.pumelo.data.im.vo.user.UserVo;
import io.pumelo.redis.ObjectRedis;
import io.pumelo.utils.BeanUtils;
import io.pumelo.utils.EncryptionUtils;
import io.pumelo.utils.jwt.JwtConstant;
import io.pumelo.utils.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserEntityRepo userEntityRepo;

    @Autowired
    private AuthService authService;

    @Autowired
    private ObjectRedis objectRedis;


    /**
     * 登录
     *
     * @return
     */
    public ApiResponse<AccessTokenVo> login(String uid, String password) {
        UserEntity userEntity =userEntityRepo.findByUid(uid);
        if(null == userEntity) {
            return ApiResponse.prompt(IMCode.ACCOUNT_NOT_EXISTS);
        }
        if (!userEntity.isAuthentication(password)){//密码输入有误
            return ApiResponse.prompt(IMCode.ACCOUNT_PWD_ERROR);
        }
        try {
            long nowMillis = System.currentTimeMillis();
            String accessToken = JwtUtils.createJWT(JwtConstant.JWT_SECRET,nowMillis,JwtConstant.JWT_ID,userEntity.getUid() , JwtConstant.JWT_TTL);
            long expiresAt = System.currentTimeMillis()+JwtConstant.JWT_TTL;
            AccessTokenVo accessTokenVo = new AccessTokenVo(accessToken,"bearer",expiresAt/1000);
            objectRedis.add("user/"+userEntity.getUid(),JwtConstant.JWT_TTL/1000/60,accessTokenVo);
            return ApiResponse.ok(accessTokenVo);
        } catch (Exception e) {
            e.printStackTrace();
            //删除已经装在的资源
            objectRedis.delete("user/"+userEntity.getUid());
            return ApiResponse.prompt(IMCode.LOGIN_FAIL);
        }
    }


    /**
     * 注册
     *
     * @return
     */
    @Transactional
    public ApiResponse<UserVo> register(String name, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setSalt(UUID.randomUUID().toString());
        userEntity.setName(name);
        userEntity.setPassword(EncryptionUtils.sha1(password+userEntity.getSalt()));
        userEntity.setUid(UUID.randomUUID().toString());//FIXME 改成数字ID
        userEntity = userEntityRepo.save(userEntity);
        return ApiResponse.ok(BeanUtils.copyAttrs(new UserVo(),userEntity));
    }

    /**
     * 退出
     */
    public ApiResponse logout() {
        objectRedis.delete("/user"+authService.getId());
        return ApiResponse.prompt(IMCode.SC_OK);
    }


    public ApiResponse search(String keyword) {
        return null;
    }
}
