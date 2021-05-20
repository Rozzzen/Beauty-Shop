package com.zhuk.beautyshop.domain.shop;

import com.zhuk.beautyshop.domain.user.Master;
import com.zhuk.beautyshop.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="master_id", nullable=false)
    @ToString.Exclude
    private Master master;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name="service_id", nullable=false)
    private ClientService service;

    @DateTimeFormat
    private LocalDateTime dateTime;

    private String reviewCode = UUID.randomUUID().toString();

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDone = false;

    @Column(columnDefinition = "boolean default false")
    private Boolean isReviewed = false;

    @Transient
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;
}
