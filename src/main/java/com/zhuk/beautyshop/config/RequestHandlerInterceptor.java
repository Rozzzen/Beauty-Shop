package com.zhuk.beautyshop.config;

import com.zhuk.beautyshop.dto.RequestContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
public class RequestHandlerInterceptor implements HandlerInterceptor {

    private RequestContext requestContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getHeader("JdbcTemplate") != null)
            requestContext.setJdbcHeaderValue(request.getHeader("JdbcTemplate"));
        return true;
    }
}
