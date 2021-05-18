package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.shop.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class JdbcClientServiceRepo {

    private final JdbcTemplate jdbcTemplate;

    public List<ClientService> findAll() {
        return jdbcTemplate.query("SELECT * FROM services", new BeanPropertyRowMapper<>(ClientService.class));
    }
}
