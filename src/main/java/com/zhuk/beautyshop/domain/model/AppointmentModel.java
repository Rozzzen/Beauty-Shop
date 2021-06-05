package com.zhuk.beautyshop.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentModel {
    private Long id;
//    private MasterModel master;
//    private UserModel user;
    private FavourTranslationModel favourTranslation;
    private LocalDateTime timeslot;
    private String reviewCode = UUID.randomUUID().toString();
    private Boolean isDone = false;
    private Boolean isReviewed = false;
}
