package com.zhuk.beautyshop.repo.mapper;

import com.zhuk.beautyshop.domain.Favour;
import com.zhuk.beautyshop.domain.FavourCategory;
import com.zhuk.beautyshop.domain.FavourTranslation;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FavourTranslationRowMapper implements RowMapper<FavourTranslation> {
    @Override
    public FavourTranslation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return FavourTranslation.builder()
                .id(rs.getLong("ft.id"))
                .favour(
                        Favour.builder()
                                .id(rs.getLong("f.id"))
                                .price(rs.getDouble("price"))
                                .category(FavourCategory.valueOf(rs.getString("category")))
                                .appointments(null)
                                .build()
                )
                .language(rs.getString("language"))
                .text(rs.getString("text"))
                .build();
    }
}
