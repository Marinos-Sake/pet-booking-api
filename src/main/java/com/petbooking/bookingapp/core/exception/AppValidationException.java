package com.petbooking.bookingapp.core.exception;


public class AppValidationException extends AppGenericException {
    private static final String DEFAULT_CODE = "VALIDATION";


    public AppValidationException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
