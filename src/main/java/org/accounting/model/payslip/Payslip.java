package org.accounting.model.payslip;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Payslip {

    String MONTHLY_PAY_FREQUENCY = "Monthly";

    String getEmployeeFirstName();

    String getEmployeeLastName();

    LocalDate getPayDate();

    String getPayFrequency();

    BigDecimal getAnnualIncome();

    BigDecimal getGrossIncome();

    BigDecimal getIncomeTax();

    BigDecimal getNetIncome();

    BigDecimal getSuperAmount();

    BigDecimal getPay();
}
