package com.zhuk.beautyshop.service;

import com.zhuk.beautyshop.domain.entity.Review;
import com.zhuk.beautyshop.repo.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepo reviewRepo;

    public void save(Review review) {
        reviewRepo.save(review);
    }
}
