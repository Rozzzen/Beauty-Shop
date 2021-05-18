package com.zhuk.beautyshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Configuration
public class RequestHeaderConfig {

    private HttpServletRequest request;

    @Autowired
    public RequestHeaderConfig(HttpServletRequest request) {
        this.request = request;
    }

    @Bean
    @RequestScope
    @Qualifier("HeaderEnumeration")
    public Enumeration<String> getHeaderEnumeration() {
        return request.getHeaderNames();
    }
}
