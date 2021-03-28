package com.twitter.consumer.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LanguageValidatorImpl.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface LanguageValidator {
    String message() default "Invalid language";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
