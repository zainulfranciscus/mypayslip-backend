package org.accounting.controller.validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

import static java.lang.String.format;

public class RequestValidator {
    public static final String CANNOT_BE_LESS_THAN_0 = "%s cannot be less than 0 ";
    public static final String CANNOT_BE_EMPTY = "%s cannot be empty ";
    protected static final String MUST_BE_A_NUMBER = "%s must be a number. Please check that %s does not have any non-numerical characters ";
    protected static final String SUPER_RATE = "Super Rate";
    public static final String SALARY = "Salary";
    protected static final String LAST_NAME = "Last name";
    protected static final String FIRST_NAME = "First name";
    protected static final String IS_NOT_VALID_DATE = "Year: %s, month: %s, date: %s are not valid date";
    protected StringBuilder builder = new StringBuilder();

    public void validateSalary(String salary) {

        salary = StringUtils.trim(salary);

        if (StringUtils.isBlank(salary)) {
            builder.append(mustNotBeBlank(SALARY));
            return;
        }

        if (!NumberUtils.isParsable(salary)) {
            builder.append(mustBeAnumber(SALARY));
            return;
        }

        BigDecimal s = new BigDecimal(salary);

        if (isLessThan0(salary)) {
            builder.append(cannotBeLessThan0(SALARY));
        }

    }

    protected boolean isLessThan0(String aNumber) {
        BigDecimal s = new BigDecimal(aNumber);
        return s.doubleValue() < 0;
    }

    public void validateFirstName(String s) {
        if (StringUtils.isBlank(s)) {
            builder.append(mustNotBeBlank(FIRST_NAME));
            return;
        }
    }

    public void validateLastName(String lastName) {
        if (StringUtils.isBlank(lastName)) {
            builder.append(mustNotBeBlank(LAST_NAME));
            return;
        }
    }

    public void validateSuperRate(String superRate) {

        if (StringUtils.isBlank(superRate)) {
            builder.append(mustNotBeBlank(SUPER_RATE));
            return;
        }
        if (!NumberUtils.isParsable(superRate)) {
            builder.append(mustBeAnumber(SUPER_RATE));
            return;
        }

        if (isLessThan0(superRate)) {
            builder.append(cannotBeLessThan0(SUPER_RATE));
        }
    }

    public String validate() {
        String validationErrors = builder.toString();
        builder = new StringBuilder();
        return validationErrors;
    }

    public boolean hasErrors() {
        return builder.toString().length() > 0;
    }

    protected static String mustNotBeBlank(String attribute) {
        return format(CANNOT_BE_EMPTY, attribute);
    }

    public static String mustBeAnumber(String attribute) {
        return format(MUST_BE_A_NUMBER, attribute, attribute);
    }

    public static String cannotBeLessThan0(String attribute) {
        return format(CANNOT_BE_LESS_THAN_0, attribute);
    }
}
