package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import com.zhuk.beautyshop.domain.user.Master;
import com.zhuk.beautyshop.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface MasterRepo extends JpaRepository<Master, Long> {

    Page<Master> findAll(Pageable pageable);

    Page<Master> findAllByUserInfoIsNot(User userInfo, Pageable pageable);

    Optional<Master> findFirstById(Long id);

    Page<Master> findAllByUserInfoIsNotAndSpecialitiesContaining(User userInfo, ServiceCategory serviceCategory, Pageable pageable);

    Page<Master> findAllBySpecialitiesContaining(ServiceCategory serviceCategory, Pageable pageable);

    Page<Master> findAllByUserInfoIsNotAndIdNotIn(User userInfo, Collection<Long> id, Pageable pageable);

    Page<Master> findAllByIdNotIn(Collection<Long> id, Pageable pageable);

    Page<Master> findAllByUserInfoIsNotAndIdNotInAndSpecialitiesContaining(User userInfo, Collection<Long> id, ServiceCategory specialities, Pageable pageable);

    Page<Master> findAllByIdNotInAndSpecialitiesContaining(Collection<Long> id, ServiceCategory specialities, Pageable pageable);

    Optional<Master> getFirstByUserInfoId(Long userInfo_id);

    Optional<Master> findFirstByUserInfoEmail(String userInfo_email);
}
