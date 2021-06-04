package com.zhuk.beautyshop.repo;

import com.zhuk.beautyshop.domain.FavourTranslation;
import com.zhuk.beautyshop.domain.FavourCategory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("JpaClientServiceRepo")
public interface JpaFavourRepo extends JpaRepository<FavourTranslation, Long>, FavourRepo {
    Optional<FavourTranslation> findByFavourIdAndLanguage(Long id, String language);

    List<FavourTranslation> findAllByLanguage(String language);

    List<FavourTranslation> findAllByFavourCategoryInAndLanguage(List<FavourCategory> category, String language);

}
