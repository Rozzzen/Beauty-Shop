package com.zhuk.beautyshop.service;

import com.zhuk.beautyshop.domain.user.MasterRating;
import com.zhuk.beautyshop.repo.MasterRatingRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MasterRatingService {

    MasterRatingRepo masterRatingRepo;

    public MasterRating getOne(Long id) {
        return masterRatingRepo.getOne(id);
    }

    public void save(MasterRating masterRating) {
        masterRatingRepo.save(masterRating);
    }
}
