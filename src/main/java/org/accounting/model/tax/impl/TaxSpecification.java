package org.accounting.model.tax.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

import static org.accounting.model.tax.impl.TaxEntity.NO_MAX_INCOME;

public class TaxSpecification implements ConstraintValidator<TaxValidator,BigDecimal> {

    private String taxAttribute;
    @Override
    public void initialize(TaxValidator taxValidator) {
        this.taxAttribute=taxValidator.value();
    }

    @Override
    public boolean isValid(BigDecimal taxAttributes, ConstraintValidatorContext constraintValidatorContext) {
        return taxAttributes.doubleValue() >= 0 || taxAttributes.equals(NO_MAX_INCOME);
    }
}
