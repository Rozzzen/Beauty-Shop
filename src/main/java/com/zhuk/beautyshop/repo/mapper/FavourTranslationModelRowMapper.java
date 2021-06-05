package com.zhuk.beautyshop.repo.mapper;

import com.zhuk.beautyshop.domain.entity.FavourCategory;
import com.zhuk.beautyshop.domain.model.FavourModel;
import com.zhuk.beautyshop.domain.model.FavourTranslationModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FavourTranslationModelRowMapper implements RowMapper<FavourTranslationModel> {
    @Override
    public FavourTranslationModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return FavourTranslationModel.builder()
                .id(rs.getLong("ft.id"))
                .favour(
                        FavourModel.builder()
                                .id(rs.getLong("f.id"))
                                .price(rs.getDouble("price"))
                                .category(FavourCategory.valueOf(rs.getString("category")))
//                                .appointments(null)
                                .build()
                )
                .language(rs.getString("language"))
                .text(rs.getString("text"))
                .build();
    }
}
