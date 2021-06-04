package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.FavourTranslation;
import com.zhuk.beautyshop.domain.FavourCategory;
import com.zhuk.beautyshop.dto.RequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class FavourRepoStrategy implements FavourRepo {

    private final JdbcFavourRepo jdbcClientServiceRepo;
    private final RequestContext requestContext;
    private final JpaFavourRepo jpaClientServiceRepo;

    public List<FavourTranslation> findAllByLanguage(String language) {
        if(!requestContext.isJdbcHeaderNull())
            return jdbcClientServiceRepo.findAllByLanguage(language);
        return jpaClientServiceRepo.findAllByLanguage(language);
    }

    public List<FavourTranslation> findAllByFavourCategoryInAndLanguage(List<FavourCategory> category, String language) {
        if(!requestContext.isJdbcHeaderNull())
            return jdbcClientServiceRepo.findAllByFavourCategoryInAndLanguage(category, language);
        return jpaClientServiceRepo.findAllByFavourCategoryInAndLanguage(category, language);
    }

    public Optional<FavourTranslation> findByFavourIdAndLanguage(Long id, String language) {
        if(!requestContext.isJdbcHeaderNull())
            return jdbcClientServiceRepo.findByFavourIdAndLanguage(id, language);
        return jpaClientServiceRepo.findByFavourIdAndLanguage(id, language);
    }
}
