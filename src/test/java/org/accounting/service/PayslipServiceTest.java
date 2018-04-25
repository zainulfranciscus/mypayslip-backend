package org.accounting.service;

import org.accounting.model.employee.Employee;
import org.accounting.model.payslip.PayslipBuilder;
import org.accounting.model.payslip.impl.PayslipEntity;
import org.accounting.model.tax.impl.TaxEntity;
import org.accounting.repositories.PayslipRepository;
import org.accounting.repositories.TaxRepository;
import org.accounting.service.impl.PayslipServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class PayslipServiceTest {

    private Employee employee;
    private String firstName;
    private String lastName;
    private int paymentMonth = 12;
    private BigDecimal salary = new BigDecimal(25000);
    private BigDecimal superRate = new BigDecimal(0.09);

    private LocalDate paymentDate = LocalDate.of(2018, 03, paymentMonth);
    private PayslipServiceImpl payslipService;
    private PayslipRepository mockPayslipRepository;

    @Before
    public void setup(){
        firstName = "John";
        lastName = "Doe";
        paymentMonth = 12;
        paymentDate = LocalDate.of(2018, 03, paymentMonth);
        employee = new Employee(firstName, lastName, paymentDate,salary,superRate);
        mockPayslipRepository = mock(PayslipRepository.class);

        payslipService = new PayslipServiceImpl();
        payslipService.setRepository(mockPayslipRepository);


    }
    @Test
    public void shouldUseMatchingNameAndMonthInTheRepository(){

        PayslipBuilder builder = new PayslipBuilder();
        builder.withLastName(lastName);
        builder.withFirstName(firstName);
        builder.withPayDate(paymentDate);
        when(mockPayslipRepository.findMatchingPayslips(firstName,lastName,paymentMonth)).thenReturn(builder.build());
        payslipService.findPayslip(employee);

        verify(mockPayslipRepository,times(1)).findMatchingPayslips(firstName,lastName,paymentDate.getMonthValue());


    }

    @Test
    public void shouldCalledRepositorySaveMethod(){

        PayslipBuilder payslipBuilder = new PayslipBuilder();
        payslipBuilder.withFirstName(firstName);
        payslipBuilder.withLastName(lastName);
        payslipBuilder.withPayDate(paymentDate);

        PayslipEntity payslip = payslipBuilder.build();

        payslipService.save(payslip);

        verify(mockPayslipRepository,times(1)).save(payslip);

    }

    @Test
    public void shouldFindAMatchingTaxBracket_BasedOnEmployeeSalary(){
        TaxRepository mockTaxRepository = mock(TaxRepository.class);
        payslipService.setTaxRepository(mockTaxRepository);

        when(mockTaxRepository.findTaxForEmployeeWithIncome(salary)).thenReturn(new TaxEntity());
        payslipService.findTaxBracket(salary);

        verify(mockTaxRepository,times(1)).findTaxForEmployeeWithIncome(salary);
    }
}
