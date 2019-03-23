package com.cars.platform.security.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cars.platform.security.validator.LicensePlateValidator;


/*
 * Custom Annotation to inject a License Plate Validator in javax.validation context, launched during API Binding.
 * Extensible in the future for conditions.
 */
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = LicensePlateValidator.class )
public @interface LicensePlateValidation {
    String message() default "License Plate couldn't be null and has a fixed size of 7.";
    boolean mustContainCharacters() default false;
    boolean mustContainNumbers() default true;
    boolean nullable() default false;
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}