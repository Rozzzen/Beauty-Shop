package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.User;
import com.zhuk.beautyshop.domain.UserRole;
import com.zhuk.beautyshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping
    public void getRegisterForm() {
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid User user,
                                             BindingResult bindingResult) {
        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())
                || bindingResult.hasErrors()
                || userService.findByEmail(user.getEmail()) != null)
            return ResponseEntity.badRequest().build();
        user.setRole(UserRole.USER);
        userService.save(user);
        return ResponseEntity.ok().build();
    }
}
