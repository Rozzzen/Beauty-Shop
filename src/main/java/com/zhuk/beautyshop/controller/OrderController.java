package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.shop.Appointment;
import com.zhuk.beautyshop.domain.shop.ClientService;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import com.zhuk.beautyshop.domain.user.CreditCard;
import com.zhuk.beautyshop.domain.user.User;
import com.zhuk.beautyshop.service.AppointmentService;
import com.zhuk.beautyshop.service.ClientServiceService;
import com.zhuk.beautyshop.service.MailSender;
import com.zhuk.beautyshop.service.MasterService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/create-order")
@SessionAttributes(types = Appointment.class, names = {"appointment"})
public class OrderController {

    private MasterService masterService;
    private ClientServiceService clientServiceService;
    private AppointmentService appointmentService;
    private MailSender mailSender;

    @ModelAttribute("appointment")
    public Appointment getAppointment() {
        return new Appointment();
    }

    @GetMapping
    public String orderForm(@RequestParam(name = "masterId", required = false) Long masterId,
                            @RequestParam(name = "serviceId", required = false) Long serviceId,
                            @ModelAttribute("appointment") Appointment appointment,
                            Locale locale) {
        if (appointment.getMaster() == null && appointment.getService() == null && appointment.getDateTime() == null) {
            if (serviceId != null) appointment.setService(clientServiceService.findFirstById(serviceId, locale.getLanguage()));
            else if (masterId != null) appointment.setMaster(masterService.findFirstById(masterId));
        }
        return "order";
    }

    @GetMapping("/services")
    public String showServices(@ModelAttribute("appointment") Appointment appointment,
                               Model model,
                               Locale locale) {

        if (appointment.getMaster() == null)
            model.addAttribute("services", clientServiceService.findAllByLanguageAndCategory(locale.getLanguage()).entrySet());
        else
            model.addAttribute("services", clientServiceService.findAllByMasterSpecialities(locale.getLanguage(), appointment.getMaster()).entrySet());

        model.addAttribute("url", "/create-order/services");
        return "services";
    }

    @PostMapping("/services")
    public String addService(@RequestParam(name = "serviceId") Long serviceId,
                             @ModelAttribute("appointment") Appointment appointment,
                             Locale locale) {
        appointment.setService(clientServiceService.findFirstById(serviceId, locale.getLanguage()));
        return "redirect:/create-order";
    }

    @PostMapping("/services/delete")
    public String removeService(@ModelAttribute("appointment") Appointment appointment) {
        appointment.setService(null);
        return "redirect:/create-order";
    }

    @GetMapping("/masters")
    public String showMasters(@ModelAttribute("appointment") Appointment appointment,
                              Model model,
                              @RequestParam(defaultValue = "id") String sort,
                              @RequestParam(defaultValue = "0") Integer page,
                              Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        model.addAttribute("sort", sort);

        if (sort.equals("rating")) sort = "rating.averageRating";

        else if (sort.equals("name")) sort = "userInfo.name";

        Pageable pageable = PageRequest.of(page, 3, Sort.by(sort).descending());

        LocalDateTime localDateTime = appointment.getDateTime();
        ClientService clientService = appointment.getService();

        if (localDateTime != null) {
            List<Appointment> appointmentList = appointmentService.findAllByDateTime(localDateTime);
            Set<Long> masterIdList = appointmentList
                    .stream()
                    .map(x -> x.getMaster().getId())
                    .collect(Collectors.toSet());
            if (clientService == null)
                model.addAttribute("page", masterService.findAllByIdNotIn(user, masterIdList, pageable));
            else {
                String speciality = clientService.getCategory().name().toUpperCase();
                model.addAttribute("page", masterService.findAllByIdNotInAndSpecialitiesContaining(masterIdList, user, speciality, pageable));
            }
        } else {
            if (clientService == null)
                model.addAttribute("page", masterService.findAll(user, pageable));
            else {
                String speciality = clientService.getCategory().name().toUpperCase();
                model.addAttribute("page", masterService.findAllBySpecialitiesContaining(user, speciality, pageable));
            }

        }
        model.addAttribute("url", "/create-order/masters");
        model.addAttribute("services", ServiceCategory.values());
        return "masters";
    }

    @PostMapping("/masters")
    public String addMaster(@RequestParam(name = "masterId") Long masterId,
                            @ModelAttribute("appointment") Appointment appointment) {
        appointment.setMaster(masterService.findFirstById(masterId));
        return "redirect:/create-order";
    }

    @PostMapping("/masters/delete")
    public String removeMaster(@ModelAttribute("appointment") Appointment appointment) {
        appointment.setMaster(null);
        return "redirect:/create-order";
    }

    @GetMapping("/timeslots")
    public String showTimeSlots(Model model) {
        model.addAttribute("url", "/create-order/timeslots");
        return "timeslot";
    }

    @PostMapping("/timeslots")
    public String addTimeslot(@ModelAttribute("appointment") Appointment appointment,
                              Model model,
                              @RequestParam(required = false, name = "date") String date,
                              @RequestParam(required = false, name = "time") String time) {
        if (date != null) {
            appointment.setDate(LocalDate.parse(date));
            appointment.setTime(null);
        }
        if (time != null) {
            appointment.setTime(LocalTime.parse(time));
            appointment.setDateTime(LocalDateTime.of(appointment.getDate(), appointment.getTime()));
            appointment.setTime(null);
            appointment.setDate(null);
            return "redirect:/create-order";
        }
        model.addAttribute("url", "/create-order/timeslots");
        return "timeslot";
    }

    @PostMapping("/timeslots/delete")
    public String removeTimeslot(@ModelAttribute("appointment") Appointment appointment) {
        appointment.setDateTime(null);
        return "redirect:/create-order";
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute("appointment") Appointment appointment,
                             @Valid CreditCard creditCard,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes attributes,
                             SessionStatus status,
                             Authentication authentication) {

        if(bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtil.getErrors(bindingResult));
            return "order";
        }

        User user = (User) authentication.getPrincipal();
        appointment.setUser(user);

        appointmentService.save(appointment);
        status.setComplete();

        mailSender.send(appointment);

        attributes.addFlashAttribute("message", "Successfully signed up");
        return "redirect:/";
    }
}