package org.accounting.model.payslip.impl;

import org.accounting.model.payslip.Payslip;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table (name = "PAYSLIP")
public class PayslipEntity implements Payslip {

    @EmbeddedId
    private PayslipEntityPrimaryKey primaryKey;

    @NotNull
    @Column(name="payFrequency", nullable = false)
    private String payFrequency;

    @DecimalMin("0.00")
    @NotNull
    @Column(name="annualIncome", nullable = false,scale = 2)
    private BigDecimal annualIncome;

    @DecimalMin("0.00")
    @NotNull
    @Column(name="grossIncome", nullable = false,scale = 2)
    private BigDecimal grossIncome;

    @DecimalMin("0.00")
    @NotNull
    @Column(name="incomeTax", nullable = false,scale = 2)
    private BigDecimal incomeTax;

    @DecimalMin("0.00")
    @NotNull
    @Column(name="netIncome", nullable = false,scale = 2)
    private BigDecimal netIncome;

    @DecimalMin("0.00")
    @NotNull
    @Column(name="superAmount", nullable = false,scale = 2)
    private BigDecimal superAmount;

    @DecimalMin("0.00")
    @NotNull
    @Column(name="pay", nullable = false,scale = 2)
    private BigDecimal pay;

    public PayslipEntity() {
    }

    public PayslipEntity(String employeeFirstName, String employeeLastName, LocalDate payDate, String payFrequency, BigDecimal annualIncome, BigDecimal grossIncome, BigDecimal incomeTax, BigDecimal netIncome, BigDecimal superAmount, BigDecimal pay) {
        this.primaryKey = new PayslipEntityPrimaryKey(employeeFirstName,employeeLastName,payDate);

        this.payFrequency = payFrequency;
        this.annualIncome = annualIncome;
        this.grossIncome = grossIncome;
        this.incomeTax = incomeTax;
        this.netIncome = netIncome;
        this.superAmount = superAmount;
        this.pay = pay;
    }

    @Override
    public String getEmployeeFirstName() {
        return this.primaryKey.getEmployeeFirstName();
    }

    @Override
    public String getEmployeeLastName() {
        return this.primaryKey.getEmployeeLastName();
    }

    @Override
    public LocalDate getPayDate() {
        return this.primaryKey.getPayDate();
    }

    @Override
    public String getPayFrequency() {
        return payFrequency;
    }

    @Override
    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    @Override
    public BigDecimal getGrossIncome() {
        return grossIncome;
    }

    @Override
    public BigDecimal getIncomeTax() {
        return incomeTax;
    }

    @Override
    public BigDecimal getNetIncome() {
        return netIncome;
    }

    @Override
    public BigDecimal getSuperAmount() {
        return superAmount;
    }

    @Override
    public BigDecimal getPay() {
        return pay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PayslipEntity that = (PayslipEntity) o;

        return new EqualsBuilder()
                .append(primaryKey, that.primaryKey)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(primaryKey)
                .toHashCode();
    }
}
