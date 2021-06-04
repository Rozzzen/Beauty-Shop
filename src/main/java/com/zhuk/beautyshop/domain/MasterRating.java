package com.zhuk.beautyshop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "master_ratings")
public class MasterRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "rating", cascade = CascadeType.ALL)
    private Master master;

    @Range(min = 0, max = 5)
    private Double averageRating = 5D;

    private Integer ratingCount = 0;
}
