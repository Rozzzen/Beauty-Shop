package com.zhuk.beautyshop.exception.exceptions;

public class ClientServiceNotFoundException extends RuntimeException {

    public ClientServiceNotFoundException() {
    }

    public ClientServiceNotFoundException(String message) {
        super(message);
    }
}
