package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.service.ClientServiceService;
import com.zhuk.beautyshop.service.MasterService;
import lombok.AllArgsConstructor;
import lombok.var;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@AllArgsConstructor
public class ClientServiceController {

    private ClientServiceService clientServiceService;
    private MasterService masterService;

    @GetMapping(value = {"/services", "/services/{master}"})
    public String serviceList(Model model,
                              @PathVariable(name = "master", required = false) Long id,
                              Locale locale) {

        if (id == null)
            model.addAttribute("services",
                    clientServiceService.findAllByLanguageAndCategory(locale.getLanguage()).entrySet());
        else {
            var services = clientServiceService.findAllByMasterSpecialities(locale.getLanguage(), masterService.findFirstById(id)).entrySet();
            System.out.println(services);
            model.addAttribute("services", services);

        }
        model.addAttribute("masters", masterService.findAll());
        model.addAttribute("id", id);
        model.addAttribute("url", "/services");
        return "services";
    }
}
