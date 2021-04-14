package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.service.ClientServiceService;
import com.zhuk.beautyshop.service.MasterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Locale;

@Controller
@AllArgsConstructor
public class ClientServiceController {

    private ClientServiceService clientServiceService;
    private MasterService masterService;

    @GetMapping(value = {"/services", "/services/{master}"})
    public String serviceList(Model model,
                              @PathVariable(name = "master", required = false) Long id,
                              Locale locale) {

        if (id == null) model.addAttribute("services", clientServiceService.findAllByLanguageAndCategory(locale.getLanguage()).entrySet());
        else model.addAttribute("services", clientServiceService.findAllByMasterSpecialities(locale.getLanguage(), masterService.findFirstById(id)).entrySet());

        model.addAttribute("masters", masterService.findAll());
        model.addAttribute("id", id);
        model.addAttribute("url", "/services");
        return "services";
    }
}
