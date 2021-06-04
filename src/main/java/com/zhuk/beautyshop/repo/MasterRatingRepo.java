package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.MasterRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterRatingRepo extends JpaRepository<MasterRating, Long> {
}
