package edu.njit.cs631.fitness.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceValidator implements ConstraintValidator<ValidPrice, String> {
    private Pattern pattern;
    private Matcher matcher;
    private static final String PRICE_PATTERN = "^\\$?\\d+\\.\\d{2}$";

    @Override
    public void initialize(final ValidPrice constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
        return (validateEmail(username));
    }

    private boolean validateEmail(final String price) {
        pattern = Pattern.compile(PRICE_PATTERN);
        matcher = pattern.matcher(price);
        return matcher.matches();
    }
}
