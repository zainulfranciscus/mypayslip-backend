package org.accounting.model.employee;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private  String firstName = "";
    private  String lastName= "";

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private  LocalDate payDate = LocalDate.now();
    private  BigDecimal annualSalary = new BigDecimal(0);
    private  BigDecimal superRate = new BigDecimal(0);

    public Employee() {


    }

    public Employee(String fn, String ln, LocalDate payDate, BigDecimal salary, BigDecimal rate) {
        this.firstName = fn;
        this.lastName = ln;
        this.payDate = payDate;
        this.superRate = rate;
        this.annualSalary = salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public BigDecimal getAnnualSalary() {
        return annualSalary;
    }

    public BigDecimal getSuperRate() {
        return superRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return new EqualsBuilder()
                .append(firstName, employee.firstName)
                .append(lastName, employee.lastName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(firstName)
                .append(lastName)
                .toHashCode();
    }
}
