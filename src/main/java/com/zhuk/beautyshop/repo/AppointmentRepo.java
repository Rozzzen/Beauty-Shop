package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.shop.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByDateTime(LocalDateTime dateTime);

    Appointment getFirstByReviewCode(String reviewCode);

    void deleteById(Long id);

    Appointment findFirstById(Long id);
}
