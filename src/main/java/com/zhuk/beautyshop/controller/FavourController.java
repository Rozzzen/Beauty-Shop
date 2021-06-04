package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.FavourCategory;
import com.zhuk.beautyshop.dto.FavourDto;
import com.zhuk.beautyshop.repo.JdbcFavourRepo;
import com.zhuk.beautyshop.service.FavourService;
import com.zhuk.beautyshop.service.MasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FavourController {

    private final FavourService favourService;
    private final MasterService masterService;

    @GetMapping(value = {"/favours", "/favours/{master}"})
    public ResponseEntity<Map<FavourCategory, List<FavourDto>>> getServiceList(
            @PathVariable(name = "master", required = false) Long id,
            Locale locale) {

        if (id == null)
            return ResponseEntity.ok()
                    .body(favourService.findAllByLanguageAndCategory(locale.getLanguage()));
        else
            return ResponseEntity.ok()
                    .body(favourService.findAllByMasterSpecialities(locale.getLanguage(),
                            masterService.findFirstById(id)));
    }
}
