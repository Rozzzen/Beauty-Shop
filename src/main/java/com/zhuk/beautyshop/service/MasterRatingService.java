package com.zhuk.beautyshop.service;

import com.zhuk.beautyshop.domain.MasterRating;
import com.zhuk.beautyshop.repo.MasterRatingRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MasterRatingService {

    private final MasterRatingRepo masterRatingRepo;

    public MasterRating getOne(Long id) {
        return masterRatingRepo.getOne(id);
    }

    public void save(MasterRating masterRating) {
        masterRatingRepo.save(masterRating);
    }
}
