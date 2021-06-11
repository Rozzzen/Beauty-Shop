package com.zhuk.beautyshop.config;

import com.zhuk.beautyshop.dto.RequestContext;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class RequestHandlerInterceptor implements HandlerInterceptor {

    private final RequestContext requestContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getHeader("JdbcTemplate") != null)
            requestContext.setJdbcHeaderValue(request.getHeader("JdbcTemplate"));
        return true;
    }
}
