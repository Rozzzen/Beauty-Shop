package com.zhuk.beautyshop.service;

import com.zhuk.beautyshop.domain.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Service
@RequiredArgsConstructor
public class MailSender {

    @Value("${spring.mail.username}")
    private String username;
    @Value("${application.timezone}")
    private String timezone;

    private final JavaMailSender mailSender;

    public void send(String emailTo, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        mailSender.send(mailMessage);
    }

    public void send(Appointment appointment) {
        String text = String.format(
                "Hello, %s! \n" +
                        "Thanks for using our service. Please leave a review: http://localhost:8080/review/%s",
                appointment.getUser().getName(),
                appointment.getReviewCode()
        );

//        ZonedDateTime sendAt = ZonedDateTime.of(appointment.getDateTime().plusDays(1L), ZoneId.of(timezone));
        ZonedDateTime sendAt = ZonedDateTime.now(ZoneId.of(timezone));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                send(appointment.getUser().getEmail(), "Leave review", text);
            }
        }, Date.from(sendAt.toInstant()));

    }
}
