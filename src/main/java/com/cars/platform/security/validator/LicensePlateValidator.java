package com.cars.platform.security.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.cars.platform.security.annotation.LicensePlateValidation;

/**
 * LicensePlate validator implementing Javax Contraing Validator for annotated LicensePlateValidation fields.
 */
public class LicensePlateValidator implements ConstraintValidator<LicensePlateValidation, String>
{

    private LicensePlateValidation licensePlateValidation;

    private static final String NUMBERS_PATTERN = ".*\\d+.*";
    private static final String CHARACTERS_PATTERN = ".*[a-zA-Z]+.*";

    @Override
    public void initialize(LicensePlateValidation licensePlateValidation)
    {
        this.licensePlateValidation = licensePlateValidation;
    }

    @Override
    public boolean isValid(final String licensePlate, final ConstraintValidatorContext context)
    {
        if (!licensePlateValidation.nullable() && (StringUtils.isBlank(licensePlate) || licensePlate.length() != 7))
        {
            return invalidateWithMessage("License Plate is mandatory and needs a length of 7 digits.", context);
        }

        if (StringUtils.isBlank(licensePlate))
        {
            return true;
        }

        if (licensePlateValidation.mustContainNumbers() && !licensePlate.matches(NUMBERS_PATTERN))
        {
            return invalidateWithMessage("License Plate must contain numbers.", context);
        }

        if (!licensePlateValidation.mustContainCharacters() && !licensePlate.matches(CHARACTERS_PATTERN))
        {
            return invalidateWithMessage("License Plate must contain characters.", context);
        }

        return true;
    }

    private boolean invalidateWithMessage(final String message, final ConstraintValidatorContext context)
    {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}
