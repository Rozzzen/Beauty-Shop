package com.zhuk.beautyshop.service;

import com.zhuk.beautyshop.domain.user.Review;
import com.zhuk.beautyshop.repo.ReviewRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {

    private ReviewRepo reviewRepo;

    public void save(Review review) {
        reviewRepo.save(review);
    }
}
