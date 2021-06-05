package com.zhuk.beautyshop.domain.entity;

import org.springframework.security.core.GrantedAuthority;

public enum  UserRole implements GrantedAuthority {
    USER, MASTER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
