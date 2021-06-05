package com.zhuk.beautyshop.domain.model;

import com.zhuk.beautyshop.domain.entity.FavourCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MasterModel {
    private Long id;
    private UserModel userInfo;
    private MasterRatingModel rating;
    private Set<FavourCategory> specialities;
    private List<ReviewModel> reviews;
    private String image = "default.jpg";
    private List<AppointmentModel> appointments;
}
