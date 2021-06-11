package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.entity.Appointment;
import com.zhuk.beautyshop.domain.entity.MasterRating;
import com.zhuk.beautyshop.domain.entity.Review;
import com.zhuk.beautyshop.service.AppointmentService;
import com.zhuk.beautyshop.service.MasterRatingService;
import com.zhuk.beautyshop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;

@ResponseBody
@RequestMapping("/review/{code}")
@RequiredArgsConstructor
public class ReviewController {

    private final AppointmentService appointmentService;
    private final MasterRatingService masterRatingService;
    private final ReviewService reviewService;

    @GetMapping
    public void getReviewForm() {
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createForm(@PathVariable String code,
                                           @Valid Review review,
                                           BindingResult bindingResult) {

        Appointment appointment = appointmentService.getOneByReviewCode(code);

        if(appointment.getIsReviewed() || bindingResult.hasErrors()) {
            throw new ValidationException();
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
    }
}
