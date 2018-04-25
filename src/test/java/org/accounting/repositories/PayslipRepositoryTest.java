package org.accounting.repositories;

import org.accounting.model.payslip.PayslipBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static java.math.BigDecimal.ZERO;
import static org.accounting.model.payslip.Payslip.MONTHLY_PAY_FREQUENCY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;



@RunWith(SpringRunner.class)
@DataJpaTest
public class PayslipRepositoryTest {



    @Autowired
    PayslipRepository payslipRepository;

    @Autowired
    private TestEntityManager entityManager;

    private PayslipBuilder payslipBuilder;

    private String firstName = "John";
    private String lastName = "Doe";
    private int paymentMonth = 3;
    private LocalDate payDate = LocalDate.of(2018, paymentMonth, 01);

    @Before
    public void setup() {

        payslipBuilder = new PayslipBuilder();
        payslipBuilder.withFirstName(firstName);
        payslipBuilder.withLastName(lastName);
        payslipBuilder.withPayDate(payDate);
        payslipBuilder.withAnnualIncome(ZERO);
        payslipBuilder.withGrossIncome(ZERO);
        payslipBuilder.withIncomeTax(ZERO);
        payslipBuilder.withNetIncome(ZERO);
        payslipBuilder.withSuperAmount(ZERO);
        payslipBuilder.withPay(ZERO);
        payslipBuilder.withPayFrequency(MONTHLY_PAY_FREQUENCY);

        payslipRepository.save(payslipBuilder.build());
    }
    
    @Test
    public void shouldReturn1MatchingPayslip_ForTheSameMonth_LastName_AndFirstName() {
        assertEquals(payslipBuilder.build(), payslipRepository.findMatchingPayslips(firstName, lastName, paymentMonth));
    }

    @Test
    public void shouldReturn1MatchingPayslip_evenWhenNameHasDifferentCase() {
        assertEquals(payslipBuilder.build(), payslipRepository.findMatchingPayslips(firstName.toUpperCase(), lastName.toUpperCase(), paymentMonth));
    }

    @Test
    public void shouldReturnNullPayslip_BecauseMonthDoesNotMatch(){
        assertNull(payslipRepository.findMatchingPayslips(firstName, lastName, 2));
    }

    @Test
    public void shouldReturnNullPayslip_BecauseFirstNameDoesNotMatch(){
        assertNull(payslipRepository.findMatchingPayslips("Zoe", lastName, paymentMonth));
    }

    @Test
    public void shouldReturnNullPayslip_BecauseLastNameDoesNotMatch(){
        assertNull(payslipRepository.findMatchingPayslips(firstName, "Mick", paymentMonth));
    }

}
