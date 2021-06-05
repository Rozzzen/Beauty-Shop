package com.zhuk.beautyshop.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MasterRatingModel {
    private Long id;
    private MasterModel master;
    private Double averageRating = 5D;
    private Integer ratingCount = 0;
}
