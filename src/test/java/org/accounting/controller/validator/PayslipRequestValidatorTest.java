package org.accounting.controller.validator;

import org.junit.Before;
import org.junit.Test;

import static org.accounting.controller.validator.PayslipRequestValidator.*;
import static org.junit.Assert.*;


public class PayslipRequestValidatorTest extends RequestValidatorTest{

    public static final String NEGATIVE_NUMBER = "-1";
    private PayslipRequestValidator payslipRequestValidator;

    public PayslipRequestValidatorTest() {

        super( new PayslipRequestValidator());

    }

    @Before
    public void setup(){
        payslipRequestValidator = new PayslipRequestValidator();
    }

    @Test
    public void shouldReturnError_whenGrossIncomeIsBlank(){
        payslipRequestValidator.validateGrossIncome("");
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),mustNotBeBlank(GROSS_INCOME));
    }

    @Test
    public void shouldReturnError_whenGrossIncomeIsEmptySpace(){
        payslipRequestValidator.validateGrossIncome(" ");
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),mustNotBeBlank(GROSS_INCOME));
    }

    @Test
    public void shouldReturnError_whenGrossIncomeIsNotANumber(){
        payslipRequestValidator.validateGrossIncome(NOT_A_NUMBER);
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),mustBeAnumber(GROSS_INCOME));
    }

    @Test
    public void shouldReturnError_whenGrossIncomeIsLessThan0(){
        payslipRequestValidator.validateGrossIncome(NEGATIVE_NUMBER);
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),cannotBeLessThan0(GROSS_INCOME));
    }

    @Test
    public void shouldNotReturnError_whenGrossIncomeIsANumber(){
        payslipRequestValidator.validateGrossIncome("2500.45");
        assertFalse(payslipRequestValidator.hasErrors());
    }

    @Test
    public void shouldNotReturnError_whenGrossIncomeIsANumberWithTrailingSpaces(){
        payslipRequestValidator.validateGrossIncome("5500 ");
        assertFalse(payslipRequestValidator.hasErrors());
    }

    @Test
    public void shouldReturnError_whenNetIncomeIsBlank(){
        payslipRequestValidator.validateNetIncome("");
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),mustNotBeBlank(NET_INCOME));
    }

    @Test
    public void shouldReturnError_whenNetIncomeIsEmptySpace(){
        payslipRequestValidator.validateNetIncome(" ");
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),mustNotBeBlank(NET_INCOME));
    }

    @Test
    public void shouldReturnError_whenNetIncomeIsNotANumber(){
        payslipRequestValidator.validateNetIncome(NOT_A_NUMBER);
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),mustBeAnumber(NET_INCOME));
    }

    @Test
    public void shouldReturnError_whenNetIncomeIsLessThan0(){
        payslipRequestValidator.validateNetIncome(NEGATIVE_NUMBER);
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),cannotBeLessThan0(NET_INCOME));
    }

    @Test
    public void shouldNotReturnError_whenNetIncomeIsANumber(){
        payslipRequestValidator.validateNetIncome("2500.45");
        assertFalse(payslipRequestValidator.hasErrors());
    }

    @Test
    public void shouldNotReturnError_whenNetIncomeIsANumberWithTrailingSpaces(){
        payslipRequestValidator.validateNetIncome("4003  ");
        assertFalse(payslipRequestValidator.hasErrors());
    }


    @Test
    public void shouldReturnError_whenPayIsBlank(){
        payslipRequestValidator.validatePay("");
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),mustNotBeBlank(PAY));
    }

    @Test
    public void shouldReturnError_whenPayIsEmptySpace(){
        payslipRequestValidator.validatePay(" ");
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),mustNotBeBlank(PAY));
    }

    @Test
    public void shouldReturnError_whenPayIsNotANumber(){
        payslipRequestValidator.validatePay(NOT_A_NUMBER);
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),mustBeAnumber(PAY));
    }

    @Test
    public void shouldReturnError_whenPayIsLessThan0(){
        payslipRequestValidator.validatePay(NEGATIVE_NUMBER);
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),cannotBeLessThan0(PAY));
    }

    @Test
    public void shouldNotReturnError_whenPayIsANumber(){
        payslipRequestValidator.validatePay("2500.45");
        assertFalse(payslipRequestValidator.hasErrors());
    }

    @Test
    public void shouldNotReturnError_whenPayIsANumberWithTrailingSpaces(){
        payslipRequestValidator.validatePay("1009  ");
        assertFalse(payslipRequestValidator.hasErrors());
    }



    @Test
    public void shouldReturnError_whenIncomeTaxIsBlank(){
        payslipRequestValidator.validateIncomeTax("");
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),mustNotBeBlank(INCOME_TAX));
    }

    @Test
    public void shouldReturnError_whenIncomeTaxIsEmptySpace(){
        payslipRequestValidator.validateIncomeTax(" ");
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),mustNotBeBlank(INCOME_TAX));
    }

    @Test
    public void shouldReturnError_whenIncomeTaxIsNotANumber(){
        payslipRequestValidator.validateIncomeTax(NOT_A_NUMBER);
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),mustBeAnumber(INCOME_TAX));
    }

    @Test
    public void shouldReturnError_whenIncomeTaxIsLessThan0(){
        payslipRequestValidator.validateIncomeTax(NEGATIVE_NUMBER);
        assertTrue(payslipRequestValidator.hasErrors());
        assertEquals(payslipRequestValidator.validate(),cannotBeLessThan0(INCOME_TAX));
    }

    @Test
    public void shouldNotReturnError_whenIncomeTaxIsANumber(){
        payslipRequestValidator.validateIncomeTax("2500.45");
        assertFalse(payslipRequestValidator.hasErrors());
    }

    @Test
    public void shouldNotReturnError_whenIncomeTaxIsANumberWithTrailingSpaces(){
        payslipRequestValidator.validateIncomeTax("3500 ");
        assertFalse(payslipRequestValidator.hasErrors());
    }

}
