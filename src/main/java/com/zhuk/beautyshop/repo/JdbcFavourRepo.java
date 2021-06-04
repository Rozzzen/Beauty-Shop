package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.Favour;
import com.zhuk.beautyshop.domain.FavourTranslation;
import com.zhuk.beautyshop.domain.FavourCategory;
import com.zhuk.beautyshop.repo.mapper.FavourTranslationRowMapper;
import lombok.AllArgsConstructor;
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
    private final FavourTranslationRowMapper favourTranslationRowMapper;

    @Override
    public List<FavourTranslation> findAllByLanguage(String language) {
        return jdbcTemplate.query("SELECT * FROM favours", new BeanPropertyRowMapper<>(FavourTranslation.class));
    }

    @Override
    public List<FavourTranslation> findAllByFavourCategoryInAndLanguage(List<FavourCategory> category, String language) {

        String inSql = String.join(",", Collections.nCopies(category.size(), "?"));
        String sql = String.format("SELECT * FROM favour_translation ft\n" +
                "JOIN favours f on f.id = ft.favour_id\n" +
                "WHERE f.category IN (%s)\n" +
                "AND ft.language = '%s'", inSql, language);

        String[] strings = new String[category.size()];
        for (int i = 0; i < category.size(); i++)
            strings[i] = category.get(i).name();

        return jdbcTemplate.query(sql, strings, favourTranslationRowMapper);
    }

    @Override
    public Optional<FavourTranslation> findByFavourIdAndLanguage(Long id, String language) {
        return jdbcTemplate.query("SELECT * FROM favour_translation ft\n" +
                "JOIN favours f on f.id = ft.favour_id\n" +
                "WHERE f.id = ? AND ft.language = ?", new Object[] { id, language }, favourTranslationRowMapper)
                .stream().findFirst();
    }
}
