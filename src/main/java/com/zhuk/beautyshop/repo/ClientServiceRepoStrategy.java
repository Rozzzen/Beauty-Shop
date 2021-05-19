package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.shop.ClientService;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import com.zhuk.beautyshop.dto.RequestContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

@Repository
@Primary
@AllArgsConstructor
public class ClientServiceRepoStrategy implements ClientServiceRepo {

    private JdbcClientServiceRepo jdbcClientServiceRepo;
    private RequestContext requestContext;
    private JpaClientServiceRepo jpaClientServiceRepo;

    public List<ClientService> findAll() {
        System.out.println(requestContext);
        if(!requestContext.isJdbcHeaderNull()) {
            System.out.println("jdbc");
            return jdbcClientServiceRepo.findAll();
        }
        System.out.println("jpa");
        return jpaClientServiceRepo.findAll();
    }

    public List<ClientService> findAllByCategoryIn(Collection<ServiceCategory> category) {
        return jpaClientServiceRepo.findAllByCategoryIn(category);
    }

    public ClientService findFirstById(Long id) {
        return jpaClientServiceRepo.findFirstById(id);
    }
}
