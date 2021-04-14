package com.zhuk.beautyshop.controller;

import com.zhuk.beautyshop.domain.user.User;
import com.zhuk.beautyshop.domain.user.UserRole;
import com.zhuk.beautyshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {

    private UserService userService;

    @GetMapping
    public String register() {
        return "register";
    }

    @PostMapping
    public String addUser(@Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("passwordError", "Passwords do not match");
            return "register";
        }

        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtil.getErrors(bindingResult));
            return "register";
        } else if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("alert", "User with this email already exists");
            return "register";
        }
        user.setRole(UserRole.USER);
        userService.save(user);
        return "redirect:/login";
    }
}
