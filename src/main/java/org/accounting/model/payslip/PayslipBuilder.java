package org.accounting.model.payslip;

import org.accounting.model.payslip.impl.PayslipEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PayslipBuilder {


    private String employeeFirstName;
    private String employeeLastName;
    private LocalDate payDate;
    private String payFrequency;
    private BigDecimal annualIncome;
    private BigDecimal grossIncome;
    private BigDecimal incomeTax;
    private BigDecimal netIncome;
    private BigDecimal superAmount;
    private BigDecimal pay;



    public PayslipBuilder withFirstName(String fn){
        this.employeeFirstName =fn;
        return this;
    }

    public PayslipBuilder withLastName(String ln) {
        this.employeeLastName = ln;
        return this;
    }

    public PayslipBuilder withPayDate(LocalDate pd){
        this.payDate= pd;
        return this;
    }

    public PayslipBuilder withPayFrequency(String payFrequency){
        this.payFrequency = payFrequency;
        return this;
    }

    public PayslipBuilder withAnnualIncome(BigDecimal annualIncome){
        this.annualIncome = annualIncome;
        return this;
    }

    public PayslipBuilder withGrossIncome(BigDecimal grossIncome){
        this.grossIncome = grossIncome;
        return this;
    }

    public PayslipBuilder withIncomeTax(BigDecimal incTax){
        this.incomeTax = incTax;
        return this;
    }

    public PayslipBuilder withNetIncome(BigDecimal netIncome){
        this.netIncome = netIncome;
        return this;
    }

    public PayslipBuilder withSuperAmount(BigDecimal superAmount){
        this.superAmount = superAmount;
        return this;
    }

    public PayslipBuilder withPay(BigDecimal pay){
        this.pay = pay;
        return this;
    }
    
    public PayslipEntity build(){
        return new PayslipEntity(   employeeFirstName,
          employeeLastName,
          payDate,
          payFrequency,
          annualIncome,
          grossIncome,
          incomeTax,
          netIncome,
          superAmount,
          pay);
    }
}
