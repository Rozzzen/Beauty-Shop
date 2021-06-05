package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.entity.FavourCategory;
import com.zhuk.beautyshop.domain.model.FavourTranslationModel;
import com.zhuk.beautyshop.repo.mapper.FavourTranslationModelRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Qualifier("JdbcClientServiceRepo")
public class JdbcFavourRepo implements FavourRepo {

    private final JdbcTemplate jdbcTemplate;
    private final FavourTranslationModelRowMapper favourTranslationModelRowMapper;

    @Override
    public List<FavourTranslationModel> findAllByLanguage(String language) {
        return jdbcTemplate.query("SELECT * FROM favours", new BeanPropertyRowMapper<>(FavourTranslationModel.class));
    }

    @Override
    public List<FavourTranslationModel> findAllByFavourCategoryInAndLanguage(List<FavourCategory> category, String language) {

        String inSql = String.join(",", Collections.nCopies(category.size(), "?"));
        String sql = String.format("SELECT * FROM favour_translation ft\n" +
                "JOIN favours f on f.id = ft.favour_id\n" +
                "WHERE f.category IN (%s)\n" +
                "AND ft.language = '%s'", inSql, language);

        String[] strings = new String[category.size()];
        for (int i = 0; i < category.size(); i++)
            strings[i] = category.get(i).name();

        return jdbcTemplate.query(sql, strings, favourTranslationModelRowMapper);
    }

    @Override
    public Optional<FavourTranslationModel> findByFavourIdAndLanguage(Long id, String language) {
        return jdbcTemplate.query("SELECT * FROM favour_translation ft\n" +
                "JOIN favours f on f.id = ft.favour_id\n" +
                "WHERE f.id = ? AND ft.language = ?", new Object[] { id, language }, favourTranslationModelRowMapper)
                .stream().findFirst();
    }
}
