package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.entity.*;
import com.zhuk.beautyshop.dto.FavourDto;
import com.zhuk.beautyshop.service.AppointmentService;
import com.zhuk.beautyshop.service.FavourService;
import com.zhuk.beautyshop.service.MailSender;
import com.zhuk.beautyshop.service.MasterService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/create-order")
@SessionAttributes(types = Appointment.class, names = {"appointment"})
public class OrderController {

    private final MasterService masterService;
    private final FavourService favourService;
    private final AppointmentService appointmentService;
    private final MailSender mailSender;

    @ModelAttribute("appointment")
    public Appointment getAppointment() {
        return new Appointment();
    }

    @PatchMapping
    public void getOrderForm(@RequestParam(name = "masterId", required = false) Long masterId,
                                               @RequestParam(name = "serviceId", required = false) Long serviceId,
                                               @ModelAttribute("appointment") Appointment appointment,
                                               Locale locale) {
        if (appointment.getMaster() == null && appointment.getFavourTranslation() == null && appointment.getTimeslot() == null) {
//            if (serviceId != null)
//                appointment.setFavourTranslation(favourService.findFirstEntityById(serviceId, locale.getLanguage()));
//            else if (masterId != null) appointment.setMaster(masterService.findFirstById(masterId));
        }
    }

    @GetMapping("/services")
    public Map<FavourCategory, List<FavourDto>> getServices(@ModelAttribute("appointment") Appointment appointment,
                                                                            Locale locale) {
//        if (appointment.getMaster() == null)
//            return ResponseEntity.ok(favourService.findAllByLanguageAndCategory(locale.getLanguage()));
//        return ResponseEntity.ok(favourService.findAllByMasterSpecialities(locale.getLanguage(), appointment.getMaster()));
        return null;
    }

    @PatchMapping("/services")
    public void putService(@RequestParam(name = "serviceId") Long serviceId,
                                             @ModelAttribute("appointment") Appointment appointment,
                                             Locale locale) {
//        appointment.setFavourTranslation(favourService.findFirstEntityById(serviceId, locale.getLanguage()));
    }

    @DeleteMapping("/services")
    public void deleteService(@ModelAttribute("appointment") Appointment appointment) {
        appointment.setFavourTranslation(null);
    }

    @GetMapping("/masters")
    public Page<Master> getMasters(@ModelAttribute("appointment") Appointment appointment,
                                                   @RequestParam(defaultValue = "id") String sort,
                                                   @RequestParam(defaultValue = "0") Integer page,
                                                   Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        if (sort.equals("rating")) sort = "rating.averageRating";
        else if (sort.equals("name")) sort = "userInfo.name";

        Pageable pageable = PageRequest.of(page, 3, Sort.by(sort).descending());

        LocalDateTime localDateTime = appointment.getTimeslot();
        FavourTranslation favour = appointment.getFavourTranslation();

        if (localDateTime != null) {
            List<Appointment> appointmentList = appointmentService.findAllByTimeslot(localDateTime);
            Set<Long> masterIdList = appointmentList
                    .stream()
                    .map(x -> x.getMaster().getId())
                    .collect(Collectors.toSet());
            if (favour == null)
                return masterService.findAllByIdNotIn(user, masterIdList, pageable);
            else {
                String speciality = favour.getFavour().getCategory().name().toUpperCase();
                return masterService.findAllByIdNotInAndSpecialitiesContaining(masterIdList, user, speciality, pageable);
            }
        } else {
            if (favour == null)
                return masterService.findAll(user, pageable);
            else {
                String speciality = favour.getFavour().getCategory().name().toUpperCase();
                return masterService.findAllBySpecialitiesContaining(user, speciality, pageable);
            }
        }
    }

    @PatchMapping("/masters")
    public void putMaster(@RequestParam(name = "masterId") Long masterId,
                                            @ModelAttribute("appointment") Appointment appointment) {
        appointment.setMaster(masterService.findFirstById(masterId));
    }

    @DeleteMapping("/masters")
    public void removeMaster(@ModelAttribute("appointment") Appointment appointment) {
        appointment.setMaster(null);
    }

    @GetMapping("/timeslots")
    public void getTimeSlots() {}

    @PatchMapping("/timeslots")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTimeslot(@ModelAttribute("appointment") Appointment appointment,
                                              LocalDateTime localDateTime) {
        appointment.setTimeslot(localDateTime);
    }

    @DeleteMapping("/timeslots")
    public void deleteTimeslot(@ModelAttribute("appointment") Appointment appointment) {
        appointment.setTimeslot(null);
    }

    @PostMapping("/submit")
    @ResponseStatus(HttpStatus.CREATED)
    public void createForm(@ModelAttribute("appointment") Appointment appointment,
                                             @Valid CreditCard creditCard,
                                             BindingResult bindingResult,
                                             SessionStatus status,
                                             Authentication authentication) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        User user = (User) authentication.getPrincipal();
        appointment.setUser(user);
        appointmentService.save(appointment);
        status.setComplete();
        mailSender.send(appointment);
    }
}