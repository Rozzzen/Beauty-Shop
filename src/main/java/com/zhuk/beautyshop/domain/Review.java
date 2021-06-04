package com.zhuk.beautyshop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 124, message = "Message is too long")
    private String text;

    @Range(min = 0, max = 5, message = "Should be in range[0, 5]")
    private Integer rating;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt = LocalDate.now();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "master_id", nullable=false)
    private Master master;
}
