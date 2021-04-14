package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.shop.Appointment;
import com.zhuk.beautyshop.domain.user.MasterRating;
import com.zhuk.beautyshop.domain.user.Review;
import com.zhuk.beautyshop.service.AppointmentService;
import com.zhuk.beautyshop.service.MasterRatingService;
import com.zhuk.beautyshop.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/review/{code}")
@AllArgsConstructor
public class ReviewController {

    private AppointmentService appointmentService;
    private MasterRatingService masterRatingService;
    private ReviewService reviewService;

    @GetMapping
    public String reviewForm() {
        return "review-form";
    }

    @PostMapping
    public String sendForm(@PathVariable String code,
                           @Valid Review review,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes attributes) {

        Appointment appointment = appointmentService.getOneByReviewCode(code);

        if(appointment.getIsReviewed()) {
            attributes.addFlashAttribute("alert", "You already left the review");
            return "redirect:/";
        }
        if(bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtil.getErrors(bindingResult));
            return "review-form";
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

        attributes.addFlashAttribute("message", "Review has been created successfully");
        return "redirect:/";
    }
}
