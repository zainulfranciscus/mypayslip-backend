package org.accounting.service.impl;

import org.accounting.model.employee.Employee;
import org.accounting.model.payslip.Payslip;
import org.accounting.model.payslip.impl.PayslipEntity;
import org.accounting.model.tax.Tax;
import org.accounting.repositories.PayslipRepository;
import org.accounting.repositories.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PayslipServiceImpl implements org.accounting.service.PayslipService {

    @Autowired
    private PayslipRepository payslipRepository;

    @Autowired
    private TaxRepository taxRepository;

    @Override
    public void setRepository(PayslipRepository payslipRepository) {
        this.payslipRepository= payslipRepository;
    }

    @Override
    public Payslip findPayslip(Employee employee) {
        int paymentMonth = employee.getPayDate().getMonthValue();
        return payslipRepository.findMatchingPayslips(employee.getFirstName(),employee.getLastName(),paymentMonth);
    }

    @Override
    public void save(Payslip payslip) {
        payslipRepository.save((PayslipEntity)payslip);
    }

    @Override
    public Tax findTaxBracket(BigDecimal salary) {
        return taxRepository.findTaxForEmployeeWithIncome(salary);
    }

    @Override
    public void setTaxRepository(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }
}
