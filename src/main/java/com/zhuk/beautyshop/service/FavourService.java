package com.zhuk.beautyshop.service;

import com.zhuk.beautyshop.domain.Favour;
import com.zhuk.beautyshop.domain.FavourTranslation;
import com.zhuk.beautyshop.domain.Master;
import com.zhuk.beautyshop.domain.FavourCategory;
import com.zhuk.beautyshop.dto.FavourDto;
import com.zhuk.beautyshop.exception.exceptions.FavourNotFoundException;
import com.zhuk.beautyshop.repo.FavourRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavourService {

    private final FavourRepo favourRepo;

    public List<FavourDto> findAllByLanguage(String language) {
        List<FavourTranslation> favours = favourRepo.findAllByLanguage(language);
        return favours.stream().map(this::getFavourDto).collect(Collectors.toList());
    }

    public Map<FavourCategory, List<FavourDto>> findAllByLanguageAndCategory(String language) {
        List<FavourDto> favours = findAllByLanguage(language);
        return favours.stream().collect(Collectors.groupingBy(FavourDto::getCategory));
    }

    public Map<FavourCategory, List<FavourDto>> findAllByMasterSpecialities(String language, Master master) {
        List<FavourTranslation> favours = favourRepo.findAllByFavourCategoryInAndLanguage(new ArrayList<>(master.getSpecialities()), language);
        return favours.stream().map(this::getFavourDto).collect(Collectors.groupingBy(FavourDto::getCategory));
    }

    public FavourDto findFirstById(Long id, String language) {
        FavourTranslation favour = favourRepo.findByFavourIdAndLanguage(id, language)
                .orElseThrow(() -> new FavourNotFoundException("failed to find " + language + " favour with id:" + id));
        return getFavourDto(favour);
    }

    public FavourTranslation findFirstEntityById(Long id, String language) {
        return favourRepo.findByFavourIdAndLanguage(id, language)
                .orElseThrow(() -> new FavourNotFoundException("failed to find " + language + " favour with id:" + id));
    }

    private FavourDto getFavourDto(FavourTranslation favour) {
        return FavourDto.builder().
                id(favour.getId())
                .category(favour.getFavour().getCategory())
                .price(favour.getFavour().getPrice())
                .language(favour.getLanguage())
                .title(favour.getText())
                .build();
    }
}