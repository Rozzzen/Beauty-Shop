package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.entity.FavourCategory;
import com.zhuk.beautyshop.domain.model.FavourTranslationModel;
import com.zhuk.beautyshop.dto.FavourDto;
import com.zhuk.beautyshop.service.FavourService;
import com.zhuk.beautyshop.service.MasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FavourController {

    private final FavourService favourService;
    private final MasterService masterService;
    private final MapperFacade mapperFacade;

    @GetMapping(value = {"/favours", "/favours/{master}"})
    public Map<FavourCategory, List<FavourTranslationModel>> getServiceList(
            @PathVariable(name = "master", required = false) Long id,
            Locale locale) {

        Map<FavourCategory, List<FavourTranslationModel>> favours;

        if (id == null)
            favours = favourService.findAllByLanguageAndCategory(locale.getLanguage());
        else
            favours = favourService.findAllByMasterSpecialities(locale.getLanguage(),
                    masterService.findFirstById(id));

        return favours;
    }
}
