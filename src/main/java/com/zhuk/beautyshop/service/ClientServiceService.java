package com.zhuk.beautyshop.service;

import com.zhuk.beautyshop.domain.shop.ClientService;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import com.zhuk.beautyshop.domain.user.Master;
import com.zhuk.beautyshop.exception.exceptions.ClientServiceNotFoundException;
import com.zhuk.beautyshop.repo.ClientServiceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceService {

    private ClientServiceRepo clientServiceRepo;

    public List<ClientService> findAllByLanguage(String language) {
        List<ClientService> clientServices = clientServiceRepo.findAll();
        clientServices.forEach(x -> {
            if (language.equals("en")) x.setTitle(x.getTitleEn());
            else if (language.equals("uk")) x.setTitle(x.getTitleUa());
        });
        return clientServices;
    }

    public Map<ServiceCategory, List<ClientService>> findAllByLanguageAndCategory(String language) {
        List<ClientService> clientServices = findAllByLanguage(language);
        return clientServices.stream().collect(Collectors.groupingBy(ClientService::getCategory));
    }

    public Map<ServiceCategory, List<ClientService>> findAllByMasterSpecialities(String language, Master master) {
        List<ClientService> clientServices = clientServiceRepo.findAllByCategoryIn(new ArrayList<>(master.getSpecialities()));
        clientServices.forEach(x -> {
            if (language.equals("en")) x.setTitle(x.getTitleEn());
            else if (language.equals("uk")) x.setTitle(x.getTitleUa());
        });
        return clientServices.stream().collect(Collectors.groupingBy(ClientService::getCategory));
    }

    public ClientService findFirstById(Long id, String language) {
        ClientService clientService = clientServiceRepo.findFirstById(id).orElseThrow(ClientServiceNotFoundException::new);
        if (language.equals("en")) clientService.setTitle(clientService.getTitleEn());
        else if (language.equals("uk")) clientService.setTitle(clientService.getTitleUa());
        return clientService;
    }
}
