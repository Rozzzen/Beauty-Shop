package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.entity.FavourCategory;
import com.zhuk.beautyshop.domain.model.FavourTranslationModel;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Qualifier("JpaClientServiceRepo")
public class JpaFavourRepoWrapper implements FavourRepo{

    private final JpaFavourRepo jpaFavourRepo;
    private final MapperFacade mapperFacade;

    @Override
    public Optional<FavourTranslationModel> findByFavourIdAndLanguage(Long id, String language) {
        return Optional.of(mapperFacade.map(jpaFavourRepo.findByFavourIdAndLanguage(id, language), FavourTranslationModel.class));
    }

    @Override
    public List<FavourTranslationModel> findAllByLanguage(String language) {
        return mapperFacade.mapAsList(jpaFavourRepo.findAllByLanguage(language), FavourTranslationModel.class);
    }

    @Override
    public List<FavourTranslationModel> findAllByFavourCategoryInAndLanguage(List<FavourCategory> category, String language) {
        return mapperFacade.mapAsList(jpaFavourRepo.findAllByFavourCategoryInAndLanguage(category, language), FavourTranslationModel.class);
    }
}
