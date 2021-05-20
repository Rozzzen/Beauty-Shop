package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.shop.ClientService;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public List<ClientService> findAllByCategoryIn(List<ServiceCategory> category) {
        String inSql = String.join(",", Collections.nCopies(category.size(), "?"));
        String sql = String.format("SELECT * FROM services s WHERE s.category IN (%s)", inSql);
        System.out.println(sql);
        String[] strings = new String[category.size()];
        for (int i = 0; i < category.size(); i++)
            strings[i] = category.get(i).name();

        return jdbcTemplate.query(sql, strings, new BeanPropertyRowMapper<>(ClientService.class));
    }

    @Override
    public ClientService findFirstById(Long id) {
        return null;
    }
}
