package com.zhuk.beautyshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @JoinColumn(name="favour_id", nullable=false)
    private FavourTranslation favourTranslation;
    //TODO how to resolve this

    @DateTimeFormat
    private LocalDateTime timeslot;

    private String reviewCode = UUID.randomUUID().toString();

    @Column(columnDefinition = "boolean default false")
    private Boolean isDone = false;

    @Column(columnDefinition = "boolean default false")
    private Boolean isReviewed = false;
}
