package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.shop.ClientService;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ClientServiceRepo extends JpaRepository<ClientService, Long> {

    List<ClientService> findAllByCategoryIn(Collection<ServiceCategory> category);

    ClientService findFirstById(Long id);
}
