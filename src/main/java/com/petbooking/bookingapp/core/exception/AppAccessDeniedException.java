package com.petbooking.bookingapp.core.exception;


public class AppAccessDeniedException extends AppGenericException {
    private static final String DEFAULT_CODE = "ACCESS_DENIED";

    public AppAccessDeniedException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
