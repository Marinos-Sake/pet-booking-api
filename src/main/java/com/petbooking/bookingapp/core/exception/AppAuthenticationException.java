package com.petbooking.bookingapp.core.exception;


public class AppAuthenticationException extends AppGenericException {
    private static final String DEFAULT_CODE = "Authentication";

    public AppAuthenticationException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }

}
