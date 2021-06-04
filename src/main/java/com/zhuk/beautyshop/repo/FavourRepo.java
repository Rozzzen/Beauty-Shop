package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.FavourTranslation;
import com.zhuk.beautyshop.domain.FavourCategory;

import java.util.List;
import java.util.Optional;

public interface FavourRepo {
    Optional<FavourTranslation> findByFavourIdAndLanguage(Long id, String language);

    List<FavourTranslation> findAllByLanguage(String language);

    List<FavourTranslation> findAllByFavourCategoryInAndLanguage(List<FavourCategory> category, String language);
}
