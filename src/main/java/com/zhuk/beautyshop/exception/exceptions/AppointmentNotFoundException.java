package com.zhuk.beautyshop.exception.exceptions;

public class AppointmentNotFoundException extends RuntimeException {

    public AppointmentNotFoundException() {
    }

    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
