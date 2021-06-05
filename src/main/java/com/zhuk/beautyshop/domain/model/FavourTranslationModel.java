package com.zhuk.beautyshop.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavourTranslationModel {
    private Long id;
    private FavourModel favour;
    private String language;
    private String text;
}

