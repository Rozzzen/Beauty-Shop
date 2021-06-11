package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.entity.*;
import com.zhuk.beautyshop.service.AppointmentService;
import com.zhuk.beautyshop.service.MasterService;
import com.zhuk.beautyshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ResponseBody
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final AppointmentService appointmentService;
    private final MasterService masterService;
    private final UserService userService;

    @GetMapping("/appointments")
    public Page<Appointment> getAppointmentList(Locale locale,
                                                              @RequestParam(defaultValue = "0") Integer page) {
        return appointmentService.findAllLocalised(locale.getLanguage(), PageRequest.of(page, 5));
    }

    @DeleteMapping("/appointments/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteById(id);
    }

    @GetMapping("/master-assignment")
    public void getMasterAssignmentForm() {

    }

    @PostMapping("/master-assignment")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMasterAssignment(@RequestParam("email") String email,
                                                         @RequestParam(value = "hairdo", required = false) String hairdo,
                                                         @RequestParam(value = "manicure", required = false) String manicure,
                                                         @RequestParam(value = "pedicure", required = false) String pedicure,
                                                         @RequestParam(value = "makeup", required = false) String makeup) {
        User user = userService.findByEmail(email);
        Set<FavourCategory> serviceCategories =
                Stream.of(hairdo, makeup, pedicure, manicure).filter(Objects::nonNull).map(x -> FavourCategory.valueOf(x.toUpperCase())).collect(Collectors.toSet());

        if (serviceCategories.isEmpty() && user == null && masterService.findByEmail(email) != null) {
            throw new ValidationException();
        }
        Master master = new Master();
        master.setUserInfo(user);
        master.setRating(new MasterRating());
        master.setSpecialities(serviceCategories);
        masterService.save(master);
    }

    @GetMapping("/appointments/select-timeslot/{appointmentId}")
    public Appointment getTimeslots(@PathVariable("appointmentId") Long id) {
        return appointmentService.findById(id);
    }

    @PutMapping("/appointments/select-timeslot")
    public void updateTimeslot(@RequestParam("appointmentId") Long id,
                                              LocalDateTime localDateTime) {
        Appointment appointment = appointmentService.getOne(id);
        appointment.setTimeslot(localDateTime);
        appointmentService.save(appointment);
    }
}
