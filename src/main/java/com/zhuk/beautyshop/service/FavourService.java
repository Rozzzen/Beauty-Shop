package com.zhuk.beautyshop.service;

import com.zhuk.beautyshop.domain.entity.FavourCategory;
import com.zhuk.beautyshop.domain.entity.Master;
import com.zhuk.beautyshop.domain.model.FavourTranslationModel;
import com.zhuk.beautyshop.exception.exceptions.FavourNotFoundException;
import com.zhuk.beautyshop.repo.FavourRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavourService {

    private final FavourRepo favourRepo;

    public List<FavourTranslationModel> findAllByLanguage(String language) {
        return favourRepo.findAllByLanguage(language);
    }

    public Map<FavourCategory, List<FavourTranslationModel>> findAllByLanguageAndCategory(String language) {
        List<FavourTranslationModel> favours = findAllByLanguage(language);
        return favours.stream().collect(Collectors.groupingBy(x -> x.getFavour().getCategory()));
    }

    public Map<FavourCategory, List<FavourTranslationModel>> findAllByMasterSpecialities(String language, Master master) {
        List<FavourTranslationModel> favours = favourRepo.findAllByFavourCategoryInAndLanguage(new ArrayList<>(master.getSpecialities()), language);
        return favours.stream().collect(Collectors.groupingBy(x -> x.getFavour().getCategory()));
    }

    public FavourTranslationModel findFirstById(Long id, String language) {
        return favourRepo.findByFavourIdAndLanguage(id, language)
                .orElseThrow(() -> new FavourNotFoundException("failed to find " + language + " favour with id:" + id));
    }

    public FavourTranslationModel findFirstEntityById(Long id, String language) {
        return favourRepo.findByFavourIdAndLanguage(id, language)
                .orElseThrow(() -> new FavourNotFoundException("failed to find " + language + " favour with id:" + id));
    }
}