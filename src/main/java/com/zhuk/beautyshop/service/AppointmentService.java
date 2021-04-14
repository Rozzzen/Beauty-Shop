package com.zhuk.beautyshop.service;

import com.zhuk.beautyshop.domain.shop.Appointment;
import com.zhuk.beautyshop.repo.AppointmentRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService {

    private AppointmentRepo appointmentRepo;

    public List<Appointment> findAllByDateTime(LocalDateTime dateTime) {
        return appointmentRepo.findAllByDateTime(dateTime);
    }

    public void save(Appointment appointment) {
        appointmentRepo.save(appointment);
    }

    public Appointment getOneByReviewCode(String reviewCode) {
        return appointmentRepo.getFirstByReviewCode(reviewCode);
    }

    public Appointment getOne(Long id) {
        return appointmentRepo.getOne(id);
    }

    public Page<Appointment> findAllLocalised(String language, Pageable pageable) {
        List<Appointment> appointments = appointmentRepo.findAll(Sort.by(Sort.Direction.DESC, "dateTime"));
        appointments.forEach(x -> {
                    if (language.equals("en")) x.getService().setTitle(x.getService().getTitleEn());
                    else if (language.equals("uk")) x.getService().setTitle(x.getService().getTitleUa());
                }
        );

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), appointments.size());

        return new PageImpl<>(appointments.subList(start, end), pageable, appointments.size());
    }

    public void deleteById(Long id) {
        appointmentRepo.deleteById(id);
    }

    public Appointment findById(Long id) {
        return appointmentRepo.findFirstById(id);
    }
}
