package com.zhuk.beautyshop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Data
@NoArgsConstructor
public class RequestContext {

    private String jdbcHeaderValue;

    public boolean isJdbcHeaderNull() {
        return jdbcHeaderValue == null;
    }
}
