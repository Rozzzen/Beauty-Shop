package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.Appointment;
import com.zhuk.beautyshop.domain.Master;
import com.zhuk.beautyshop.domain.User;
import com.zhuk.beautyshop.service.AppointmentService;
import com.zhuk.beautyshop.service.MasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MasterController {

    @Value("${application.timezone}")
    private String timezone;

    private final MasterService masterService;
    private final AppointmentService appointmentService;

    @GetMapping(value = {"/masters", "/masters/{category}"})
    public ResponseEntity<Page<Master>> getMasterList(@RequestParam(defaultValue = "id") String sort,
                                                   @RequestParam(defaultValue = "0") Integer page,
                                                   @PathVariable(name = "category", required = false) String category,
                                                   Authentication authentication) {
        User user;
        if (authentication == null) user = null;
        else user = (User) authentication.getPrincipal();
        if (sort.equals("rating")) sort = "rating.averageRating";
        else if (sort.equals("name")) sort = "userInfo.name";

        Pageable pageable = PageRequest.of(page, 3, Sort.by(sort).descending());

        if (category == null)
            return ResponseEntity.ok().body(masterService.findAll(user, pageable));
        return ResponseEntity.ok()
                .body(masterService.findAllBySpecialitiesContaining(user, category.toUpperCase(), pageable));
    }

    @GetMapping(value = {"/masters/info/{id}"})
    public ResponseEntity<Master> getMasterDetailInformation(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(masterService.findFirstById(id));
    }

    @GetMapping("/masters/image")
    @PreAuthorize("hasAuthority('MASTER')")
    public void getMasterImageForm() {}

    @PatchMapping(value = {"/masters/image"})
    @PreAuthorize("hasAuthority('MASTER')")
    public void setMasterImage(@RequestParam("file") MultipartFile file,
                               Authentication authentication) throws IOException {

        User user = (User) authentication.getPrincipal();
        Master master = masterService.getFirstByUserInfoId(user.getId());

        masterService.updateMasterImage(file, master);
        masterService.save(master);
    }

    @GetMapping("/masters/schedule")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<List<Appointment>> getMasterSchedule(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Master master = masterService.getFirstByUserInfoId(user.getId());

//        ZoneId zoneId = ZoneId.of(timezone);
//        List<LocalDate> localDates = new ArrayList<>();
//        localDates.add(LocalDate.now(zoneId).minusDays(1L));
//        for (int i = 0; i < 7; i++)
//            localDates.add(LocalDate.now(zoneId).plusDays(i));
//        model.addAttribute("weak", localDates);

        return ResponseEntity.ok().body(master.getAppointments());
    }

    @PatchMapping("/masters/schedule")
    @PreAuthorize("hasAuthority('MASTER')")
    public void setCompleted(@RequestParam("appointmentId") Long id) {
        Appointment appointment = appointmentService.getOne(id);
        appointment.setIsDone(true);
        appointmentService.save(appointment);
    }
}
