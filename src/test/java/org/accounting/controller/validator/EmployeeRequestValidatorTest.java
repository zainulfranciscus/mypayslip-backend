package org.accounting.controller.validator;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EmployeeRequestValidatorTest extends RequestValidatorTest {

    private EmployeeRequestValidator validator;

    public EmployeeRequestValidatorTest() {
        super( new EmployeeRequestValidator());


    }

    @Before
    public void setup(){
        validator = new EmployeeRequestValidator();
    }

    @Test
    public void shouldHasValidationError_WhenYearIsNotANumber(){
        validator.validatePayDate(NOT_A_NUMBER,"03","01");
        assertTrue(validator.hasErrors());
        assertEquals(validator.errorMessageForInvalidDates(NOT_A_NUMBER,"03","01"),validator.validate());

    }

    @Test
    public void shouldHasValidationError_WhenMonthIsNotANumber(){
        validator.validatePayDate("2018",NOT_A_NUMBER,"01");
        assertTrue(validator.hasErrors());
        assertEquals(validator.errorMessageForInvalidDates("2018",NOT_A_NUMBER,"01"),validator.validate());

    }

    @Test
    public void shouldHasValidationError_WhenDateIsNotANumber(){
        validator.validatePayDate("2018","03",NOT_A_NUMBER);
        assertTrue(validator.hasErrors());
        assertEquals(validator.errorMessageForInvalidDates("2018","03",NOT_A_NUMBER),validator.validate());

    }

    @Test
    public void shouldHasValidationError_WhenDateComponentsAreNotAValidDay(){
        validator.validatePayDate("-1","-1","-1");
        assertTrue(validator.hasErrors());
        assertEquals(validator.errorMessageForInvalidDates("-1","-1","-1"),validator.validate());

    }
}
