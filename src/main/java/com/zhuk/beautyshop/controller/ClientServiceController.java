package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.repo.ClientServiceJdbcTemplate;
import com.zhuk.beautyshop.service.ClientServiceService;
import com.zhuk.beautyshop.service.MasterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Locale;

@Controller
@AllArgsConstructor
public class ClientServiceController {

    private ClientServiceService clientServiceService;
    private MasterService masterService;
    private ClientServiceJdbcTemplate clientServiceJdbcTemplate;

    @GetMapping(value = {"/services", "/services/{master}"})
    public String serviceList(Model model,
                              @PathVariable(name = "master", required = false) Long id,
                              @RequestHeader HttpHeaders httpHeader,
                              Locale locale) {

        System.out.println(httpHeader.get("JdbcTemplate"));
        System.out.println(httpHeader.containsKey("JdbcTemplate"));
        if (id == null) {
            if (httpHeader.containsKey("JdbcTemplate"))
                model.addAttribute("services", clientServiceJdbcTemplate.findAll(locale.getLanguage()).entrySet());
            else
                model.addAttribute("services", clientServiceService.findAllByLanguageAndCategory(locale.getLanguage()).entrySet());
        } else
            model.addAttribute("services", clientServiceService.findAllByMasterSpecialities(locale.getLanguage(), masterService.findFirstById(id)).entrySet());

        model.addAttribute("masters", masterService.findAll());
        model.addAttribute("id", id);
        model.addAttribute("url", "/services");
        return "services";
    }
}
