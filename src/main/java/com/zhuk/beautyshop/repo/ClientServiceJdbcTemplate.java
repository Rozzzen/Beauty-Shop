package com.zhuk.beautyshop.repo;

import ch.qos.logback.core.net.server.Client;
import com.zhuk.beautyshop.domain.shop.ClientService;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ClientServiceJdbcTemplate {

    private final JdbcTemplate jdbcTemplate;

    public Map<ServiceCategory, List<ClientService>> findAll(String language) {
        List<ClientService> clientServices = jdbcTemplate.query("SELECT * FROM services", new BeanPropertyRowMapper<>(ClientService.class));
        clientServices.forEach(x -> {
            if (language.equals("en")) x.setTitle(x.getTitleEn());
            else if (language.equals("uk")) x.setTitle(x.getTitleUa());
        });
        return clientServices.stream().collect(Collectors.groupingBy(ClientService::getCategory));
    }
}
