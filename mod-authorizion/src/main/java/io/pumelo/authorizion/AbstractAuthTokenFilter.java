package io.pumelo.authorizion;


import com.alibaba.fastjson.JSON;
import io.pumelo.common.errorcode.Syscode;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.utils.AccesstokenUtil;
import io.pumelo.utils.IdealTokenUtils;
import io.pumelo.utils.SystemInfoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权token过滤器抽象类
 * 只有加了 {@link AuthFilter} 的mapping才会纳入token授权机制。
 * 默认不会拦截任何mapping来校验身份，这样的好处就是不影响其他第三方的servlet
 */
public abstract class AbstractAuthTokenFilter implements HandlerInterceptor,Security {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //对RestApi鉴权
        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
            AuthFilter authFilterAnnotation = method.getMethodAnnotation(AuthFilter.class);
            if(authFilterAnnotation == null){
                return true;
            }
            if(authFilterAnnotation.skip()){
                return true;
            }
            //检查token是否合法
            if(isAccessTokenInvaild()){
                responseMsg(response, Syscode.JWT_TOKEN_INVALID);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }

    protected abstract String getJwtSecret();

    @Override
    public String getJwtId() {
        if (isAccessTokenInvaild())
            return null;
        String auth = getAuth();
        if (StringUtils.isBlank(auth)){
            return null;
        }
        return IdealTokenUtils.getJwtId(getJwtSecret(), auth);
    }

    public String getAuth(){
        HttpServletRequest request = getHttpRequest();
        if (request==null)return null;
        return request.getHeader("Authorization");
    }

    public HttpServletRequest getHttpRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    @Override
    public String getSubjectFromAccessToken() {
        if (isAccessTokenInvaild())return null;
        String auth = getAuth();
        if (org.apache.commons.lang3.StringUtils.isBlank(auth)){
            return null;
        }
        return IdealTokenUtils.getSubject(getJwtSecret(), auth);
    }

    @Override
    public boolean isTimeout() {
        return false;
    }

    @Override
    public boolean isAccessTokenInvaild() {
        HttpServletRequest request = getHttpRequest();
        String auth = request.getHeader("Authorization");
        return  AccesstokenUtil.isAccessTokenInvaild(auth, getJwtSecret());
    }

    @Override
    public String getIp() {
        return SystemInfoUtils.getIpAddr(getHttpRequest());
    }

    @Override
    public String getMac() {
        return SystemInfoUtils.getMacAddress(getIp());
    }

    private static void responseMsg(ServletResponse response, Syscode syscode){
        if (response == null)return;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setStatus(200);
        ApiResponse apiResponse = ApiResponse.prompt(syscode);
        try {
            httpResponse.getWriter().write(JSON.toJSONString(apiResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
