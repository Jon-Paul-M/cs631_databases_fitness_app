package edu.njit.cs631.medical.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SSNValidator implements ConstraintValidator<ValidSSN, String> {
    private Pattern pattern;
    private Matcher matcher;
    private static final String SSN_PATTERN = "^\\d{3}-\\d{2}-\\d{4}$";

    @Override
    public void initialize(final ValidSSN constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String ssn, final ConstraintValidatorContext context) {
        return (validateSSN(ssn));
    }

    private boolean validateSSN(final String ssn) {
        pattern = Pattern.compile(SSN_PATTERN);
        matcher = pattern.matcher(ssn);
        return matcher.matches();
    }
}
