package com.zhuk.beautyshop.service;

import com.zhuk.beautyshop.domain.entity.Appointment;
import com.zhuk.beautyshop.exception.exceptions.AppointmentNotFoundException;
import com.zhuk.beautyshop.repo.AppointmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepo appointmentRepo;

    public List<Appointment> findAllByTimeslot(LocalDateTime dateTime) {
        return appointmentRepo.findAllByTimeslot(dateTime);
    }

    public void save(Appointment appointment) {
        appointmentRepo.save(appointment);
    }

    public Appointment getOneByReviewCode(String reviewCode) {
        return appointmentRepo.getFirstByReviewCode(reviewCode)
                .orElseThrow(() -> new AppointmentNotFoundException("failed to find appointment with review code:" + reviewCode));
    }

    public Appointment getOne(Long id) {
        return appointmentRepo.getOne(id);
    }

    public Page<Appointment> findAllLocalised(String language, Pageable pageable) {
        List<Appointment> appointments = appointmentRepo.findAllByFavourTranslationLanguage(language, Sort.by(Sort.Direction.DESC, "dateTime"));

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), appointments.size());

        return new PageImpl<>(appointments.subList(start, end), pageable, appointments.size());
    }

    public void deleteById(Long id) {
        appointmentRepo.deleteById(id);
    }

    public Appointment findById(Long id) {
        return appointmentRepo.findFirstById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("failed to find appointment with id:" + id));
    }
}
