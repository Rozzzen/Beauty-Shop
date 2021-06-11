package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.entity.FavourCategory;
import com.zhuk.beautyshop.domain.entity.FavourTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface JpaFavourRepo extends JpaRepository<FavourTranslation, Long> {
    Optional<FavourTranslation> findByFavourIdAndLanguage(Long id, String language);

    List<FavourTranslation> findAllByLanguage(String language);

    List<FavourTranslation> findAllByFavourCategoryInAndLanguage(List<FavourCategory> category, String language);

}
