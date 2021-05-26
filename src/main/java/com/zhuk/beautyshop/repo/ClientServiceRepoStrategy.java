package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.shop.ClientService;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import com.zhuk.beautyshop.dto.RequestContext;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
@AllArgsConstructor
public class ClientServiceRepoStrategy implements ClientServiceRepo {

    private JdbcClientServiceRepo jdbcClientServiceRepo;
    private RequestContext requestContext;
    private JpaClientServiceRepo jpaClientServiceRepo;

    public List<ClientService> findAll() {
        if(!requestContext.isJdbcHeaderNull()) {
            System.out.println("jdbc");
            return jdbcClientServiceRepo.findAll();
        }
        System.out.println("jpa");
        return jpaClientServiceRepo.findAll();
    }

    public List<ClientService> findAllByCategoryIn(List<ServiceCategory> category) {
        if(!requestContext.isJdbcHeaderNull()) {
            System.out.println("jdbc");
            return jdbcClientServiceRepo.findAllByCategoryIn(category);
        }
        System.out.println("jpa");
        return jpaClientServiceRepo.findAllByCategoryIn(category);
    }

    public Optional<ClientService> findFirstById(Long id) {
        return jpaClientServiceRepo.findFirstById(id);
    }
}
