package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.shop.ClientService;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

@Repository
public class ClientServiceRepo {

    private JdbcClientServiceRepo jdbcClientServiceRepo;
    private Enumeration<String> headerEnumeration;
    private JpaClientServiceRepo jpaClientServiceRepo;

    @Autowired
    public ClientServiceRepo(JdbcClientServiceRepo jdbcClientServiceRepo,
                             @Qualifier("HeaderEnumeration") Enumeration<String> headerEnumeration,
                             JpaClientServiceRepo jpaClientServiceRepo) {
        this.jdbcClientServiceRepo = jdbcClientServiceRepo;
        this.headerEnumeration = headerEnumeration;
        this.jpaClientServiceRepo = jpaClientServiceRepo;
    }

    public List<ClientService> findAll() {
        while(headerEnumeration.hasMoreElements())
            if("jdbctemplate".equals(headerEnumeration.nextElement())) {
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
