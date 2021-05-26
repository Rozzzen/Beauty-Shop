package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.shop.Appointment;
import com.zhuk.beautyshop.domain.user.MasterRating;
import com.zhuk.beautyshop.domain.user.Review;
import com.zhuk.beautyshop.service.AppointmentService;
import com.zhuk.beautyshop.service.MasterRatingService;
import com.zhuk.beautyshop.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/review/{code}")
@AllArgsConstructor
public class ReviewController {

    private final AppointmentService appointmentService;
    private final MasterRatingService masterRatingService;
    private final ReviewService reviewService;

    @GetMapping
    public void getReviewForm() {
    }

    @PostMapping
    public ResponseEntity<Object> createForm(@PathVariable String code,
                                           @Valid Review review,
                                           BindingResult bindingResult) {

        Appointment appointment = appointmentService.getOneByReviewCode(code);

        if(appointment.getIsReviewed() || bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        MasterRating masterRating = masterRatingService.getOne(appointment.getMaster().getRating().getId());
        Integer ratingCount = masterRating.getRatingCount();
        masterRating.setAverageRating(
                (masterRating.getAverageRating() * ratingCount + review.getRating())
                        /
                        (ratingCount + 1));
        masterRating.setRatingCount(ratingCount + 1);
        masterRatingService.save(masterRating);

        review.setUser(appointment.getUser());
        review.setMaster(appointment.getMaster());
        reviewService.save(review);

        appointment.setIsReviewed(true);
        appointmentService.save(appointment);

        return ResponseEntity.ok().build();
    }
}
