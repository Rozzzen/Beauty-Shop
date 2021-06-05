package com.zhuk.beautyshop.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewModel {
    private Long id;
    private String text;
    private Integer rating;
    private LocalDate createdAt = LocalDate.now();
    private UserModel user;
    private MasterModel master;
}
