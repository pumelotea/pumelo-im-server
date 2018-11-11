package io.pumelo.im.filter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  跨域过滤器
 */
public class SimpleCorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type");
        if(httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpResponse.setStatus(HttpStatus.OK.value());
            return;
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

}
