package org.accounting.model.payslip;

import org.accounting.model.payslip.impl.PayslipEntity;
import org.accounting.model.payslip.impl.PayslipEntityPrimaryKey;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PayslipEntityTest {

    private final static BigDecimal NEGATIVE_NUMBER = new BigDecimal(-1);

    @Autowired
    private TestEntityManager em;

    private String employeeFirstName;
    private String employeeLastName;
    private LocalDate payDate;
    private BigDecimal annualIncome;

    private PayslipBuilder payslipBuilder;
    private Payslip payslip;


    @Before
    public void setup() {
        employeeFirstName = "John";
        employeeLastName = "Doe";
        payDate = LocalDate.of(2018, 03, 01);
        annualIncome = new BigDecimal(65000);

        payslipBuilder = new PayslipBuilder();

        BigDecimal grossIncome = new BigDecimal(5000);
        BigDecimal incomeTax = new BigDecimal(450);
        BigDecimal netIncome = new BigDecimal(50000);
        BigDecimal superAmount = new BigDecimal(4500);
        BigDecimal pay = new BigDecimal(5600);
        String payFrequency = "Monthly";

        payslipBuilder.withAnnualIncome(annualIncome);
        payslipBuilder.withFirstName(employeeFirstName);
        payslipBuilder.withLastName(employeeLastName);
        payslipBuilder.withGrossIncome(grossIncome);
        payslipBuilder.withIncomeTax(incomeTax);
        payslipBuilder.withNetIncome(netIncome);
        payslipBuilder.withSuperAmount(superAmount);
        payslipBuilder.withPay(pay);
        payslipBuilder.withPayDate(payDate);
        payslipBuilder.withPayFrequency(payFrequency);

        payslip = payslipBuilder.build();

    }

    @Test
    public void testByPrimaryKey_shouldReturnPayslipWithMatchingAttributes() {

        em.persist(payslip);
        PayslipEntity payslip = em.find(PayslipEntity.class, new PayslipEntityPrimaryKey(employeeFirstName, employeeLastName, payDate));

        assertEquals(payslip.getAnnualIncome(), annualIncome);
        assertEquals(payslip.getEmployeeFirstName(), employeeFirstName);
        assertEquals(payslip.getEmployeeLastName(), employeeLastName);
        assertEquals(payslip.getPayDate(), payDate);


    }

    @Test
    public void twoPayslipAreEqual_whenFirstName_LastName_AndPaydate_IsEqual() {
        payslipBuilder.withFirstName(employeeFirstName);
        payslipBuilder.withLastName(employeeLastName);
        payslipBuilder.withPayDate(payDate);

        assertEquals(payslip, payslipBuilder.build());
    }

    @Test
    public void thesePayslipAreNotEqual_BecauseFirstNameIsDifferent() {
        payslipBuilder.withFirstName("Zoe");
        payslipBuilder.withLastName(employeeLastName);
        payslipBuilder.withPayDate(payDate);

        assertNotSame(payslip, payslipBuilder.build());
    }

    @Test
    public void thesePayslipAreNotEqual_BecauseLastNameIsDifferent() {
        payslipBuilder.withFirstName(employeeFirstName);
        payslipBuilder.withLastName("Mark");
        payslipBuilder.withPayDate(payDate);

        assertNotSame(payslip, payslipBuilder.build());
    }

    @Test
    public void thesePayslipAreNotEqual_BecausePayDateIsDifferent() {
        payslipBuilder.withFirstName(employeeFirstName);
        payslipBuilder.withLastName(employeeLastName);
        payslipBuilder.withPayDate(LocalDate.of(2016, 01, 01));

        assertNotSame(payslip, payslipBuilder.build());
    }

    @Test
    public void savingNegativeAnnualSalary_ShouldThrowAnError() {
        payslipBuilder.withAnnualIncome(NEGATIVE_NUMBER);
        Payslip payslip = payslipBuilder.build();

        assertThatConstraintViolationExceptionHappenned(payslip);
    }

    @Test
    public void savingNegativeGross_ShouldThrowAnError() {
        payslipBuilder.withGrossIncome(NEGATIVE_NUMBER);
        Payslip payslip = payslipBuilder.build();

        assertThatConstraintViolationExceptionHappenned(payslip);
    }

    @Test
    public void savingNegativeIncomeTax_ShouldThrowAnError() {
        payslipBuilder.withIncomeTax(NEGATIVE_NUMBER);
        Payslip payslip = payslipBuilder.build();

        assertThatConstraintViolationExceptionHappenned(payslip);
    }

    @Test
    public void savingNegativeNetIncome_ShouldThrowAnError() {
        payslipBuilder.withNetIncome(NEGATIVE_NUMBER);
        Payslip payslip = payslipBuilder.build();

        assertThatConstraintViolationExceptionHappenned(payslip);
    }

    @Test
    public void savingNegativeSuperAmount_ShouldThrowAnError() {
        payslipBuilder.withSuperAmount(NEGATIVE_NUMBER);
        Payslip payslip = payslipBuilder.build();

        assertThatConstraintViolationExceptionHappenned(payslip);
    }

    @Test
    public void savingNegativePay_ShouldThrowAnError() {
        payslipBuilder.withPay(NEGATIVE_NUMBER);
        Payslip payslip = payslipBuilder.build();

        assertThatConstraintViolationExceptionHappenned(payslip);
    }

    @Test
    public void payslipEqualityShouldIgnoreLowerAndUpperCase_InTheName(){
        Payslip p1 = payslipBuilder.build();
        Payslip p2 = payslipBuilder.withFirstName(employeeFirstName.toUpperCase()).withLastName(employeeLastName.toUpperCase()).build();

        assertEquals(p1,p2);
    }

    @Test
    public void payslipWithTheSameEmployeeName_HasEqualHashCode() {
        assertEquals(payslipBuilder.hashCode(), payslipBuilder.hashCode());
    }

    @Test
    public void payslipWithDifferentFirstName_HasDifferentHashCode() {
        assertEquals(payslipBuilder.hashCode(), payslipBuilder.withFirstName("Mark").hashCode());
    }

    @Test
    public void payslipWithDifferentLastName_HasDifferentHashCode() {
        assertNotSame(payslipBuilder.hashCode(), payslipBuilder.withFirstName("Bruno").hashCode());
    }

    @Test
    public void payslipWithDifferentPaydate_HasDifferentHashCode() {
        assertNotSame(payslipBuilder.hashCode(), payslipBuilder.withPayDate(LocalDate.of(2011,2,1)).hashCode());
    }

    private void assertThatConstraintViolationExceptionHappenned(Payslip p) {
        Executable executable = () -> {
            em.persist(p);
            em.flush();
        };
        assertThrows(ConstraintViolationException.class, executable);
    }
}
