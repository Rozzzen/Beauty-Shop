package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.shop.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByTimeslot(LocalDateTime dateTime);

    Optional<Appointment> getFirstByReviewCode(String reviewCode);

    void deleteById(Long id);

    Optional<Appointment> findFirstById(Long id);
}
