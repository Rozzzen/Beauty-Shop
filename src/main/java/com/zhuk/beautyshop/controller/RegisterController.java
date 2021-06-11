package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.entity.User;
import com.zhuk.beautyshop.domain.entity.UserRole;
import com.zhuk.beautyshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;

@ResponseBody
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping
    public void getRegisterForm() {
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid User user,
                                             BindingResult bindingResult) {
        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())
                || bindingResult.hasErrors()
                || userService.findByEmail(user.getEmail()) != null)
            throw new ValidationException();
        user.setRole(UserRole.USER);
        userService.save(user);
    }
}
