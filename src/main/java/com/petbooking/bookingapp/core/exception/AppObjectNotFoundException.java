package com.petbooking.bookingapp.core.exception;


public class AppObjectNotFoundException extends AppGenericException{

    private static final String DEFAULT_CODE = "NotFound";

    public AppObjectNotFoundException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
