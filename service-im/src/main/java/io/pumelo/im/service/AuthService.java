package io.pumelo.im.service;

import io.pumelo.authorizion.AbstractAuthTokenFilter;
import io.pumelo.authorizion.IdGetter;
import io.pumelo.authorizion.ModelGetter;
import io.pumelo.data.im.vo.user.UserVo;
import io.pumelo.redis.ObjectRedis;
import io.pumelo.utils.jwt.JwtConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AuthService extends AbstractAuthTokenFilter implements IdGetter<String>,ModelGetter<UserVo> {

    @Autowired
    private ObjectRedis redis;

    @Override
    protected String getJwtSecret() {
        return JwtConstant.JWT_SECRET;
    }

    @Override
    public String getId() {
        return getSubjectFromAccessToken();
    }

    @Override
    public UserVo getModel() {
        return redis.get("user/"+getId(), UserVo.class);
    }
}
