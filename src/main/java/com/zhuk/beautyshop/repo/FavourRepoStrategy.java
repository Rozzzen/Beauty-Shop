package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.entity.FavourCategory;
import com.zhuk.beautyshop.domain.model.FavourTranslationModel;
import com.zhuk.beautyshop.dto.RequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class FavourRepoStrategy implements FavourRepo {

    private final JdbcFavourRepo jdbcClientServiceRepo;
    private final RequestContext requestContext;
    private final JpaFavourRepoWrapper jpaFavourRepoWrapper;

    public List<FavourTranslationModel> findAllByLanguage(String language) {
        if(!requestContext.isJdbcHeaderNull())
            return jdbcClientServiceRepo.findAllByLanguage(language);
        return jpaFavourRepoWrapper.findAllByLanguage(language);
    }

    public List<FavourTranslationModel> findAllByFavourCategoryInAndLanguage(List<FavourCategory> category, String language) {
        if(!requestContext.isJdbcHeaderNull())
            return jdbcClientServiceRepo.findAllByFavourCategoryInAndLanguage(category, language);
        return jpaFavourRepoWrapper.findAllByFavourCategoryInAndLanguage(category, language);
    }

    public Optional<FavourTranslationModel> findByFavourIdAndLanguage(Long id, String language) {
        if(!requestContext.isJdbcHeaderNull())
            return jdbcClientServiceRepo.findByFavourIdAndLanguage(id, language);
        return jpaFavourRepoWrapper.findByFavourIdAndLanguage(id, language);
    }
}
