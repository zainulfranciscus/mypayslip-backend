package org.accounting.controller;

import org.accounting.model.payslip.PayslipBuilder;
import org.accounting.model.payslip.impl.PayslipEntity;
import org.accounting.model.tax.impl.TaxEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.accounting.model.payslip.Payslip.MONTHLY_PAY_FREQUENCY;

public class ControllerTest {

    public static final String SAVE_PAYSLIP_REQUEST_PARAMETERS = "/payslip/savePayslip?annualSalary=%s&grossIncome=%s&incomeTax=%s&netIncome=%s&superRate=%s&pay=%s&firstName=%s&lastName=%s";
    public static final String GET_TAX_REQUEST_PARAMETERS = "/payslip/tax?annualSalary=%s";

    protected String annualSalary = "25000.00";
    protected String firstName = "John";
    protected String lastName = "Doe";
    protected String superRate = "0.30";
    protected String grossIncome = "12600.00";
    protected String incomeTax = "500.00";
    protected String netIncome = "70.00";
    protected TaxEntity tax = new TaxEntity();
    protected String pay = "60.00";


    protected String getTaxRequest() {
        return String.format(GET_TAX_REQUEST_PARAMETERS, annualSalary);
    }

    protected String getPayslipRequest(String firstName, String lastName){
        return String.format(SAVE_PAYSLIP_REQUEST_PARAMETERS,
                annualSalary,
                grossIncome,
                incomeTax,
                netIncome,
                superRate,
                pay,
                firstName,
                lastName);
    }

    protected PayslipEntity getPayslip(){

        return getPayslipBuilder().build();
    }

    protected PayslipBuilder getPayslipBuilder(){
        return new PayslipBuilder().withAnnualIncome(new BigDecimal(annualSalary))
                .withGrossIncome(new BigDecimal(grossIncome))
                .withIncomeTax(new BigDecimal(incomeTax))
                .withNetIncome(new BigDecimal(netIncome))
                .withSuperAmount(new BigDecimal(superRate))
                .withPay(new BigDecimal(pay))
                .withPayFrequency(MONTHLY_PAY_FREQUENCY)
                .withPayDate(LocalDate.now())
                .withFirstName(this.firstName)
                .withLastName(this.lastName);
    }
}
