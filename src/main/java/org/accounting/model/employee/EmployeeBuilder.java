package org.accounting.model.employee;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeBuilder {

    private String firstName;
    private String lastName;
    private LocalDate payDate;
    private BigDecimal annualSalary;
    private BigDecimal superRate;

    public EmployeeBuilder withFirstName(String fn){
        this.firstName = fn;
        return this;
    }

    public EmployeeBuilder withLastName(String ln){
        this.lastName = ln;
        return this;
    }

    public EmployeeBuilder withPayDate(LocalDate pd){
        this.payDate = pd;
        return this;
    }

    public EmployeeBuilder withAnnualSalary(BigDecimal salary){
        this.annualSalary = salary;
        return this;
    }

    public EmployeeBuilder withSuperRate(BigDecimal rate){
        this.superRate = rate;
        return this;
    }

    public Employee build(){
        return new Employee(firstName,lastName,payDate,annualSalary,superRate);
    }
}
