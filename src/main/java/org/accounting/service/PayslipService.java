package org.accounting.service;

import org.accounting.model.employee.Employee;
import org.accounting.model.payslip.Payslip;
import org.accounting.model.tax.Tax;
import org.accounting.repositories.PayslipRepository;
import org.accounting.repositories.TaxRepository;

import java.math.BigDecimal;

public interface PayslipService {
    void setRepository(PayslipRepository payslipRepository);

    Payslip findPayslip(Employee employee);

    void save(Payslip payslip);

    Tax findTaxBracket(BigDecimal salary);

    void setTaxRepository(TaxRepository taxRepository);
}
