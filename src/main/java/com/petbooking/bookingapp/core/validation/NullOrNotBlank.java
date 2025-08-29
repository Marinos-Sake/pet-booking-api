package com.petbooking.bookingapp.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NullOrNotBlankValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NullOrNotBlank {

    // Default error message if validation fails
    String message() default "must be null or not blank";

    // Allows grouping of constraints
    Class<?>[] groups() default {};

    // Can carry metadata information about the validation (e.g. severity)
    Class<? extends Payload>[] payload() default {};
}
