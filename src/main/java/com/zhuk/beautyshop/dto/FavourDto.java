package com.zhuk.beautyshop.dto;

import com.zhuk.beautyshop.domain.FavourCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavourDto {
    private Long id;
    private Double price;
    private FavourCategory category;
    private String title;
    private String language;
}
