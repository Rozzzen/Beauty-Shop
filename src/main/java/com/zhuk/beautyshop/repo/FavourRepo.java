package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.entity.FavourCategory;
import com.zhuk.beautyshop.domain.model.FavourTranslationModel;

import java.util.List;
import java.util.Optional;

public interface FavourRepo {
    Optional<FavourTranslationModel> findByFavourIdAndLanguage(Long id, String language);

    List<FavourTranslationModel> findAllByLanguage(String language);

    List<FavourTranslationModel> findAllByFavourCategoryInAndLanguage(List<FavourCategory> category, String language);
}
