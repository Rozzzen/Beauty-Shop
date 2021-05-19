package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.shop.ClientService;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
@Qualifier("JpaClientServiceRepo")
public interface JpaClientServiceRepo extends JpaRepository<ClientService, Long>, ClientServiceRepo {

    List<ClientService> findAllByCategoryIn(Collection<ServiceCategory> category);

    ClientService findFirstById(Long id);
}
