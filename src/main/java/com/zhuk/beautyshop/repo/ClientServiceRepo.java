package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.shop.ClientService;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;

import java.util.List;
import java.util.Optional;

public interface ClientServiceRepo {
    List<ClientService> findAll();
    List<ClientService> findAllByCategoryIn(List<ServiceCategory> category);
    Optional<ClientService> findFirstById(Long id);
}
