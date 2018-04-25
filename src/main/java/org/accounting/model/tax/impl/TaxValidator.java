package org.accounting.model.tax.impl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = TaxSpecification.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface TaxValidator {
    String message() default "{org.accounting.model.mustBeGreaterThanZero}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String value() default "";
}
