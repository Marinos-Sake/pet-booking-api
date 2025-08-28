package com.petbooking.bookingapp.core.exception;


public class AppInvalidStateException extends AppGenericException{

    private static final String DEFAULT_CODE = "INVALID_STATE";

    public AppInvalidStateException(String code, String message) {
        super(code+ DEFAULT_CODE, message);
    }
}
