package com.petbooking.bookingapp.core.exception;

public class AppInvalidInputException extends AppGenericException{
    private static final String DEFAULT_CODE = "INVALID_INPUT";

    public AppInvalidInputException(String code, String message) {
        super(code+ DEFAULT_CODE, message);
    }
}
