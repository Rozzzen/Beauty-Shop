package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.shop.Appointment;
import com.zhuk.beautyshop.domain.shop.ClientService;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import com.zhuk.beautyshop.domain.user.CreditCard;
import com.zhuk.beautyshop.domain.user.Master;
import com.zhuk.beautyshop.domain.user.User;
import com.zhuk.beautyshop.service.AppointmentService;
import com.zhuk.beautyshop.service.ClientServiceService;
import com.zhuk.beautyshop.service.MailSender;
import com.zhuk.beautyshop.service.MasterService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
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
    private final ClientServiceService clientServiceService;
    private final AppointmentService appointmentService;
    private final MailSender mailSender;

    @ModelAttribute("appointment")
    public Appointment getAppointment() {
        return new Appointment();
    }

    @PatchMapping
    public ResponseEntity<Object> getOrderForm(@RequestParam(name = "masterId", required = false) Long masterId,
                                               @RequestParam(name = "serviceId", required = false) Long serviceId,
                                               @ModelAttribute("appointment") Appointment appointment,
                                               Locale locale) {
        if (appointment.getMaster() == null && appointment.getService() == null && appointment.getTimeslot() == null) {
            if (serviceId != null)
                appointment.setService(clientServiceService.findFirstById(serviceId, locale.getLanguage()));
            else if (masterId != null) appointment.setMaster(masterService.findFirstById(masterId));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/services")
    public ResponseEntity<Map<ServiceCategory, List<ClientService>>> getServices(@ModelAttribute("appointment") Appointment appointment,
                                                                                 Locale locale) {
        if (appointment.getMaster() == null)
            return ResponseEntity.ok(clientServiceService.findAllByLanguageAndCategory(locale.getLanguage()));
        return ResponseEntity.ok(clientServiceService.findAllByMasterSpecialities(locale.getLanguage(), appointment.getMaster()));
    }

    @PatchMapping("/services")
    public ResponseEntity<Object> putService(@RequestParam(name = "serviceId") Long serviceId,
                                             @ModelAttribute("appointment") Appointment appointment,
                                             Locale locale) {
        appointment.setService(clientServiceService.findFirstById(serviceId, locale.getLanguage()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/services")
    public ResponseEntity<Object> deleteService(@ModelAttribute("appointment") Appointment appointment) {
        appointment.setService(null);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/masters")
    public ResponseEntity<Page<Master>> getMasters(@ModelAttribute("appointment") Appointment appointment,
                                                   @RequestParam(defaultValue = "id") String sort,
                                                   @RequestParam(defaultValue = "0") Integer page,
                                                   Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        if (sort.equals("rating")) sort = "rating.averageRating";
        else if (sort.equals("name")) sort = "userInfo.name";

        Pageable pageable = PageRequest.of(page, 3, Sort.by(sort).descending());

        LocalDateTime localDateTime = appointment.getTimeslot();
        ClientService clientService = appointment.getService();

        if (localDateTime != null) {
            List<Appointment> appointmentList = appointmentService.findAllByTimeslot(localDateTime);
            Set<Long> masterIdList = appointmentList
                    .stream()
                    .map(x -> x.getMaster().getId())
                    .collect(Collectors.toSet());
            if (clientService == null)
                return ResponseEntity.ok(masterService.findAllByIdNotIn(user, masterIdList, pageable));
            else {
                String speciality = clientService.getCategory().name().toUpperCase();
                return ResponseEntity.ok(masterService.findAllByIdNotInAndSpecialitiesContaining(masterIdList, user, speciality, pageable));
            }
        } else {
            if (clientService == null)
                return ResponseEntity.ok(masterService.findAll(user, pageable));
            else {
                String speciality = clientService.getCategory().name().toUpperCase();
                return ResponseEntity.ok(masterService.findAllBySpecialitiesContaining(user, speciality, pageable));
            }
        }
    }

    @PatchMapping("/masters")
    public ResponseEntity<Object> putMaster(@RequestParam(name = "masterId") Long masterId,
                                            @ModelAttribute("appointment") Appointment appointment) {
        appointment.setMaster(masterService.findFirstById(masterId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/masters")
    public ResponseEntity<Object> removeMaster(@ModelAttribute("appointment") Appointment appointment) {
        appointment.setMaster(null);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/timeslots")
    public ResponseEntity<Object> getTimeSlots() {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/timeslots")
    public ResponseEntity<Object> addTimeslot(@ModelAttribute("appointment") Appointment appointment,
                                              LocalDateTime localDateTime) {
        appointment.setTimeslot(localDateTime);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/timeslots")
    public ResponseEntity<Object> deleteTimeslot(@ModelAttribute("appointment") Appointment appointment) {
        appointment.setTimeslot(null);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/submit")
    public ResponseEntity<Object> createForm(@ModelAttribute("appointment") Appointment appointment,
                                             @Valid CreditCard creditCard,
                                             BindingResult bindingResult,
                                             SessionStatus status,
                                             Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        User user = (User) authentication.getPrincipal();
        appointment.setUser(user);
        appointmentService.save(appointment);
        status.setComplete();
        mailSender.send(appointment);

        return ResponseEntity.ok().build();
    }
}