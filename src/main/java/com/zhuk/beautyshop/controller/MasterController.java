package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.shop.Appointment;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import com.zhuk.beautyshop.domain.user.Master;
import com.zhuk.beautyshop.domain.user.User;
import com.zhuk.beautyshop.service.AppointmentService;
import com.zhuk.beautyshop.service.MasterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MasterController {

    @Value("${application.timezone}")
    private String timezone;

    private MasterService masterService;
    private AppointmentService appointmentService;

    public MasterController(MasterService masterService, AppointmentService appointmentService) {
        this.masterService = masterService;
        this.appointmentService = appointmentService;
    }

    @GetMapping(value = {"/masters", "/masters/{category}"})
    public String masterList(Model model,
                             @RequestParam(defaultValue = "id") String sort,
                             @RequestParam(defaultValue = "0") Integer page,
                             @PathVariable(name = "category", required = false) String category,
                             Authentication authentication) {

        User user;
        if(authentication == null) user = null;
        else user = (User) authentication.getPrincipal();

        model.addAttribute("sort", sort);

        if (sort.equals("rating")) sort = "rating.averageRating";

        else if (sort.equals("name")) sort = "userInfo.name";

        Pageable pageable = PageRequest.of(page, 3, Sort.by(sort).descending());

        if (category == null) {
            model.addAttribute("page", masterService.findAll(user, pageable));
            model.addAttribute("url", "/masters");
        } else {
            model.addAttribute("page", masterService.findAllBySpecialitiesContaining(user, category.toUpperCase(), pageable));
            model.addAttribute("url", "/masters/" + category.toLowerCase());
        }
        model.addAttribute("services", ServiceCategory.values());
        return "masters";
    }

    @GetMapping(value = {"/masters/info/{id}"})
    public String masterInformation(@PathVariable Long id,
                                    Model model) {
        model.addAttribute("master", masterService.findFirstById(id));
        return "master-info";
    }

    @GetMapping("/masters/image")
    @PreAuthorize("hasAuthority('MASTER')")
    public String masterImageForm() {
        return "image-form";
    }

    @PostMapping(value = {"/masters/image"})
    @PreAuthorize("hasAuthority('MASTER')")
    public String setMasterImage(@RequestParam("file") MultipartFile file,
                                 Authentication authentication) throws IOException {

        User user = (User) authentication.getPrincipal();
        Master master = masterService.getFirstByUserInfoId(user.getId());

        masterService.updateMasterImage(file, master);
        masterService.save(master);

        return "redirect:/";
    }

    @GetMapping("/masters/schedule")
    @PreAuthorize("hasAuthority('MASTER')")
    public String masterSchedule(Authentication authentication,
                                 Model model) {

        User user = (User) authentication.getPrincipal();
        Master master = masterService.getFirstByUserInfoId(user.getId());

        ZoneId zoneId = ZoneId.of(timezone);

        List<LocalDate> localDates = new ArrayList<>();
        localDates.add(LocalDate.now(zoneId).minusDays(1L));
        for (int i = 0; i < 7; i++)
            localDates.add(LocalDate.now(zoneId).plusDays(i));

        model.addAttribute("appointments", master.getAppointments());
        model.addAttribute("weak", localDates);

        return "master-schedule";
    }

    @PostMapping("/masters/schedule")
    @PreAuthorize("hasAuthority('MASTER')")
    public String assignCompleted(@RequestParam("appointmentId") Long id) {
        Appointment appointment = appointmentService.getOne(id);
        appointment.setIsDone(true);
        appointmentService.save(appointment);
        return "redirect:/masters/schedule";
    }
}
