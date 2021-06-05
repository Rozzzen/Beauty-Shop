package com.zhuk.beautyshop.domain.model;

import com.zhuk.beautyshop.domain.entity.FavourCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavourModel {
    private Long id;
    private Double price;
    private FavourCategory category;
//    private List<AppointmentModel> appointments;
}
