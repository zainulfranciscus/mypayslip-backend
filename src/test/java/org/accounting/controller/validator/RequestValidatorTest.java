package org.accounting.controller.validator;


import org.junit.Ignore;
import org.junit.Test;

import static org.accounting.controller.validator.RequestValidator.*;
import static org.junit.Assert.*;

@Ignore
public class RequestValidatorTest {

    public static final String EMPTY_STRING = "";
    protected static final String NOT_A_NUMBER = "NOT_A_NUMBER";
    protected static final String BLANK_STRING = "";
    protected RequestValidator validator;


    public RequestValidatorTest(RequestValidator validator) {
        this.validator = validator;
    }

    @Test
    public void shouldHasValidationError_whenSalaryIsLessThan0() {


        validator.validateSalary("-1");
        assertTrue(validator.hasErrors());
        assertEquals(cannotBeLessThan0(SALARY), validator.validate());

    }

    @Test
    public void shouldHasValidationError_whenSalaryIsEmpty() {
        validator.validateSalary("");
        assertTrue(validator.hasErrors());
        assertEquals(mustNotBeBlank(SALARY), validator.validate());
    }

    @Test
    public void shouldHasValidationError_whenSalaryIsEmptyString() {
        validator.validateSalary(" ");
        assertTrue(validator.hasErrors());
        assertEquals(mustNotBeBlank(SALARY), validator.validate());
    }

    @Test
    public void shouldHasValidationError_whenSalaryAreAlphabets() {

        validator.validateSalary(EmployeeRequestValidatorTest.NOT_A_NUMBER);
        assertTrue(validator.hasErrors());
        assertEquals(mustBeAnumber(SALARY), validator.validate());
    }

    @Test
    public void shouldHasValidationError_whenSalaryNumbersWithSpaces() {
        validator.validateSalary("125 000");
        assertTrue(validator.hasErrors());
        assertEquals(mustBeAnumber(SALARY), validator.validate());
    }

    @Test
    public void shouldAccept_NumbersAsSalary() {
        validator.validateSalary("8000");
        assertFalse(validator.hasErrors());
    }

    @Test
    public void shouldAccept_NumbersWithTrailingSpacesAsSalary() {
        validator.validateSalary("12004 ");
        assertFalse(validator.hasErrors());
    }

    @Test
    public void shouldAccept_DecimalsAsSalary() {
        validator.validateSalary("12500.00");
        assertFalse(validator.hasErrors());
    }

    @Test
    public void shouldHasValidationError_whenFirstNameIsEmpty() {
        validator.validateFirstName(EMPTY_STRING);
        assertTrue(validator.hasErrors());
        assertEquals(mustNotBeBlank(FIRST_NAME), validator.validate());
    }

    @Test
    public void shouldHasValidationError_whenFirstNameIsASpace() {
        validator.validateFirstName("  ");
        assertTrue(validator.hasErrors());
        assertEquals(mustNotBeBlank(FIRST_NAME), validator.validate());
    }

    @Test
    public void shouldHasNoValidationError_whenFirstNameIsNotEmpty() {
        validator.validateFirstName("John");
        assertFalse(validator.hasErrors());
    }

    @Test
    public void shouldHasValidationError_whenLastNameIsASpace() {
        validator.validateLastName("  ");
        assertTrue(validator.hasErrors());
        assertEquals(mustNotBeBlank(LAST_NAME), validator.validate());
    }

    @Test
    public void shouldHasValidationError_whenLastNameIsEmpty() {

        validator.validateLastName(BLANK_STRING);
        assertTrue(validator.hasErrors());
        assertEquals(mustNotBeBlank(LAST_NAME), validator.validate());
    }

    @Test
    public void shouldHasValidationError_whenSuperIsNotANumber() {
        validator.validateSuperRate(NOT_A_NUMBER);
        assertTrue(validator.hasErrors());
        assertEquals(mustBeAnumber(SUPER_RATE), validator.validate());

    }

    @Test
    public void shouldHasValidationError_whenSuperIsBlank() {
        validator.validateSuperRate(BLANK_STRING);
        assertTrue(validator.hasErrors());
        assertEquals(mustNotBeBlank(SUPER_RATE), validator.validate());
    }
}
