package com.zhuk.beautyshop.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public String accessDeniedException(RedirectAttributes attributes) {
        attributes.addFlashAttribute("alert", "You dont own right to enter this page");
        return "redirect:/";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String pageNotFoundException(RedirectAttributes attributes) {
        attributes.addFlashAttribute("alert", "This page does not exist");
        return "redirect:/";
    }

    @ExceptionHandler(Exception.class)
    public String handleAll(RedirectAttributes attributes) {
        attributes.addFlashAttribute("alert", "Unexpected error occured");
        return "redirect:/";
    }
}
