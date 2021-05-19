package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.shop.ClientService;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
@AllArgsConstructor
@Qualifier("JdbcClientServiceRepo")
public class JdbcClientServiceRepo implements ClientServiceRepo {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<ClientService> findAll() {
        return jdbcTemplate.query("SELECT * FROM services", new BeanPropertyRowMapper<>(ClientService.class));
    }

    @Override
    public List<ClientService> findAllByCategoryIn(Collection<ServiceCategory> category) {
        return Collections.emptyList();
    }

    @Override
    public ClientService findFirstById(Long id) {
        return null;
    }
}
