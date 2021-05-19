package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.shop.ClientService;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;

import java.util.Collection;
import java.util.List;

public interface ClientServiceRepo {
    List<ClientService> findAll();
    List<ClientService> findAllByCategoryIn(Collection<ServiceCategory> category);
    ClientService findFirstById(Long id);
}
