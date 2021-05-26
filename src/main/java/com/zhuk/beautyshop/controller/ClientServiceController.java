package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.shop.ClientService;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import com.zhuk.beautyshop.service.ClientServiceService;
import com.zhuk.beautyshop.service.MasterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@AllArgsConstructor
public class ClientServiceController {

    private final ClientServiceService clientServiceService;
    private final MasterService masterService;

    @GetMapping(value = {"/services", "/services/{master}"})
    public ResponseEntity<Map<ServiceCategory, List<ClientService>>> getServiceList(
            @PathVariable(name = "master", required = false) Long id,
            Locale locale) {

        if (id == null)
            return ResponseEntity.ok()
                    .body(clientServiceService.findAllByLanguageAndCategory(locale.getLanguage()));
        else
            return ResponseEntity.ok()
                    .body(clientServiceService.findAllByMasterSpecialities(locale.getLanguage(),
                            masterService.findFirstById(id)));
    }
}
