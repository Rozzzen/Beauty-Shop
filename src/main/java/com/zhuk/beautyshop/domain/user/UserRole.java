package com.zhuk.beautyshop.domain.user;

import org.springframework.security.core.GrantedAuthority;

public enum  UserRole implements GrantedAuthority {
    USER, MASTER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
