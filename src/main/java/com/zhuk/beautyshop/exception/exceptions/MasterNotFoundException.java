package com.zhuk.beautyshop.exception.exceptions;

public class MasterNotFoundException extends RuntimeException{
    public MasterNotFoundException() {
    }

    public MasterNotFoundException(String message) {
        super(message);
    }
}
