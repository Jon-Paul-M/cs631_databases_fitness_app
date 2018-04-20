package edu.njit.cs631.fitness.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = SSNValidator.class)
@Documented
public @interface ValidSSN {

    String message() default "Invalid SSN (please include dashes)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
