package com.twitter.consumer.validator;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class LanguageValidatorImpl implements
        ConstraintValidator<LanguageValidator, String> {

    @Value("#{'${allowed.languages}'.split(',')}")
    private List<String> langList;

    @Override
    public void initialize(LanguageValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(String lang, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = false;
        if (lang != null) {
            valid = langList.contains(lang);
        }
        return valid;
    }
}
