package com.petbooking.bookingapp.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullOrNotBlankValidator
        implements ConstraintValidator<NullOrNotBlank, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {

        if (value == null) return true;

        // Otherwise -> must not be empty or only whitespace
        return value.toString().trim().length() > 0;
    }
}
