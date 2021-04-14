package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.shop.Appointment;
import com.zhuk.beautyshop.domain.shop.ServiceCategory;
import com.zhuk.beautyshop.domain.user.Master;
import com.zhuk.beautyshop.domain.user.MasterRating;
import com.zhuk.beautyshop.domain.user.User;
import com.zhuk.beautyshop.service.AppointmentService;
import com.zhuk.beautyshop.service.MasterService;
import com.zhuk.beautyshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private AppointmentService appointmentService;
    private MasterService masterService;
    private UserService userService;

    @GetMapping("/appointments")
    public String appointmentsList(Model model,
                                   Locale locale,
                                   @RequestParam(defaultValue = "0") Integer page) {

        Pageable pageable = PageRequest.of(page, 5);

        model.addAttribute("page", appointmentService.findAllLocalised(locale.getLanguage(), pageable));
        model.addAttribute("url", "/admin/appointments");
        model.addAttribute("sort", "none");
        return "appointments";
    }

    @PostMapping("/appointments/cancel")
    public String cancelAppointment(@RequestParam("appointmentId") Long id) {
        appointmentService.deleteById(id);
        return "redirect:/admin/appointments";
    }

    @GetMapping("/master-assignment")
    public String masterAssignmentPage(Model model) {
        model.addAttribute("specialities", ServiceCategory.values());
        return "master-assignment";
    }

    @PostMapping("/master-assignment")
    public String masterAssignment(@RequestParam("email") String email,
                                   RedirectAttributes attributes,
                                   @RequestParam(value = "hairdo", required = false) String hairdo,
                                   @RequestParam(value = "manicure", required = false) String manicure,
                                   @RequestParam(value = "pedicure", required = false) String pedicure,
                                   @RequestParam(value = "makeup", required = false) String makeup) {
        User user = userService.findByEmail(email);
        if(user == null) {
            attributes.addFlashAttribute("alert", "Could not find user with this email");
            return "redirect:/admin/master-assignment";
        }
        if(masterService.findByEmail(email) != null) {
            attributes.addFlashAttribute("alert", "Master already exists");
            return "redirect:/admin/master-assignment";
        }
        Set<ServiceCategory> serviceCategories =
                Stream.of(hairdo, makeup, pedicure, manicure).filter(Objects::nonNull).map(x -> ServiceCategory.valueOf(x.toUpperCase())).collect(Collectors.toSet());

        if(serviceCategories.isEmpty()) {
            attributes.addFlashAttribute("alert", "Master must have at least one speciality");
            return "redirect:/admin/master-assignment";
        }
        Master master = new Master();
        master.setUserInfo(user);
        master.setRating(new MasterRating());
        master.setSpecialities(serviceCategories);
        masterService.save(master);
        attributes.addFlashAttribute("message", "master successfully created");
        return "redirect:/";
    }

    @GetMapping("/appointments/select-timeslot")
    public String selectTimeslot(@RequestParam("appointmentId") Long id,
                                 Model model) {
        model.addAttribute("appointment", appointmentService.findById(id));
        model.addAttribute("id", id);
        model.addAttribute("url", "/admin/appointments/select-timeslot");
        return "timeslot";
    }

    @PostMapping("/appointments/select-timeslot")
    public String addTimeslot(@RequestParam("appointmentId") Long id,
                              @RequestParam(required = false) String date,
                              @RequestParam(required = false) String time,
                              Model model) {
        if (date != null) {
            model.addAttribute("date", LocalDate.parse(date, DateTimeFormatter.ISO_DATE));
        }
        if (time != null) {
            LocalTime localTime = LocalTime.parse(time);
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
            Appointment appointment = appointmentService.getOne(id);
            appointment.setDateTime(localDateTime);
            appointmentService.save(appointment);
            return "redirect:/admin/appointments";
        }
        model.addAttribute("appointment", appointmentService.findById(id));
        model.addAttribute("id", id);
        model.addAttribute("url", "/admin/appointments/select-timeslot");
        return "timeslot";
    }
}
