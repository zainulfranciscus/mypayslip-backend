package org.accounting.model.payslip.impl;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class PayslipEntityPrimaryKey implements Serializable {

    private String employeeFirstName;
    private String employeeLastName;
    private LocalDate payDate;

    public PayslipEntityPrimaryKey() {}

    public PayslipEntityPrimaryKey(String employeeFirstName, String employeeLastName, LocalDate payDate) {
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.payDate = payDate;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PayslipEntityPrimaryKey that = (PayslipEntityPrimaryKey) o;

        return new EqualsBuilder()
                .append(employeeFirstName.toLowerCase(), that.employeeFirstName.toLowerCase())
                .append(employeeLastName.toLowerCase(), that.employeeLastName.toLowerCase())
                .append(payDate, that.payDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(employeeFirstName.toLowerCase())
                .append(employeeLastName.toLowerCase())
                .append(payDate)
                .toHashCode();
    }
}
