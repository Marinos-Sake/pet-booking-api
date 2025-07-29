package com.petbooking.bookingapp.core.exception;

import lombok.Getter;

@Getter
public class AppGenericException extends RuntimeException {
    private final String code;

    public AppGenericException(String code, String message) {
        super(message);
        this.code = code;
    }

}
