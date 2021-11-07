package com.tapu.shorterurl.unique;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { UniqueShortUrlValidator.class})
public @interface UniqueShortUrl {
    String message() default "Short Url must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
