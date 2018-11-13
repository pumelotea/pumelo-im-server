package io.pumelo.permission;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.pumelo.authorizion.AbstractAuthTokenFilter;
import io.pumelo.common.errorcode.Syscode;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.backend.struct.SessionUser;
import io.pumelo.data.backend.vo.LoginVo;
import io.pumelo.permission.annotation.PermissionFilter;
import io.pumelo.permission.annotation.PermissionRegister;
import io.pumelo.redis.ObjectRedis;
import io.pumelo.utils.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 权限注解实现支持
 * 以及过滤器
 * 默认不拦截任何mapping
 * 只有加了{@link PermissionFilter}注解的Mapping才会纳入权限拦截
 * 这样的做的好处就是该拦截器不影响第三方的servlet映射的mapping。
 *
 */
public abstract class AbstractPermission implements HandlerInterceptor,Permission {
    @Autowired
    private ObjectRedis redis;

    @Autowired
    private AbstractAuthTokenFilter abstractAuthTokenFilter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            if(!this.isActive()){
                return true;
            }
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            PermissionFilter classFilter = handlerMethod.getMethodAnnotation(PermissionFilter.class);
            if(null == classFilter) {
                return true;
            }
            if(classFilter.skip()){
                return true;
            }
            String userId = abstractAuthTokenFilter.getSubjectFromAccessToken();
            if(userId == null){
                responseMsg(response, Syscode.USER_SESSION_EXPIRAT);
                return false;
            }
            SessionUser<LoginVo> sessionUser = redis.get("user/" + userId,new TypeReference<SessionUser<LoginVo>>(){});
            if(sessionUser == null){
                responseMsg(response, Syscode.USER_SESSION_EXPIRAT);
                return false;
            }
            if(!authentication(handlerMethod,sessionUser)){
                responseMsg(response, Syscode.CLIENT_NOT_AUTHORIZATION);
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

    /**
     * 生成菜单数据
     * @return
     */
    public List<Object[]> buildMenus() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = getHandlerMethods();
        List<Object[]> patternAndPermssion = new ArrayList<>();
        handlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
            Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
            PermissionRegister permissionRegister = handlerMethod.getMethodAnnotation(PermissionRegister.class);
            if(null != permissionRegister) {
                RequestMapping requestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
                Object[] objects = new Object[3];
                objects[0] = patterns.stream().findFirst().get();
                objects[1] = permissionRegister;
                objects[2] = requestMapping.method()[0] + "";
                patternAndPermssion.add(objects);
            }
        });
        return patternAndPermssion;
    }

    /**
     * 判断控制器Type是否有 {@link PermissionFilter} 注解
     * @param handlerMethod
     * @return
     */
    private boolean isExistsClassFilter(HandlerMethod handlerMethod) {
        PermissionFilter classFilter = handlerMethod.getMethodAnnotation(PermissionFilter.class);
        if(null != classFilter) {
            return true;
        }
        return false;
    }

    /**
     * 获取url映射相关信息
     * @return
     */
    private Map<RequestMappingInfo, HandlerMethod> getHandlerMethods() {
        RequestMappingHandlerMapping requestMapping = SpringContextHolder.getBean(RequestMappingHandlerMapping.class);
        return requestMapping.getHandlerMethods();
    }

    /**
     * Permission鉴权
     * @param handlerMethod
     * @return
     */
    public boolean authentication(HandlerMethod handlerMethod, SessionUser<LoginVo> sessionUser) {
        RequestMapping methodAnnotation = handlerMethod.getMethodAnnotation(RequestMapping.class);
        String method = methodAnnotation.method()[0].name();
        String uri = methodAnnotation.value()[0];
        return sessionUser != null && sessionUser.isAccessPermission(method,uri);
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
