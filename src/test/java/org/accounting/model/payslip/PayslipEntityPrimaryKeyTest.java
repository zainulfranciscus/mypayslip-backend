package org.accounting.model.payslip;

import org.accounting.model.payslip.impl.PayslipEntityPrimaryKey;
import org.junit.Test;

import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;

public class PayslipEntityPrimaryKeyTest {

    @Test
    public void twoPayslipEntityPK_areEqual_whenFirstAndLastName_AndPayDate_AreEqual() {
        LocalDate ld = LocalDate.now();
        String firstName = "John";
        String lastName = "Doe";
        PayslipEntityPrimaryKey payslipEntityPrimaryKey1 = new PayslipEntityPrimaryKey(firstName, lastName, ld);
        PayslipEntityPrimaryKey payslipEntityPrimaryKey2 = new PayslipEntityPrimaryKey(firstName, lastName, ld);

        assertEquals(payslipEntityPrimaryKey1,payslipEntityPrimaryKey2);
        assertEquals(payslipEntityPrimaryKey1.hashCode(),payslipEntityPrimaryKey2.hashCode());

    }

    @Test
    public void twoPayslipEntityPK_areEqual_whenNamesIsDifferent() {
        LocalDate ld = LocalDate.now();
        String firstName = "John";
        PayslipEntityPrimaryKey payslipEntityPrimaryKey1 = new PayslipEntityPrimaryKey(firstName, "Zoe", ld);
        PayslipEntityPrimaryKey payslipEntityPrimaryKey2 = new PayslipEntityPrimaryKey(firstName, "Bruce", ld);

        assertNotSame(payslipEntityPrimaryKey1,payslipEntityPrimaryKey2);
        assertNotSame(payslipEntityPrimaryKey1.hashCode(),payslipEntityPrimaryKey2.hashCode());

    }


    @Test
    public void twoPayslipEntityPK_areNotEqual_whenPayDate_IsDifferent() {
        LocalDate ld = LocalDate.now();
        LocalDate ld2 = LocalDate.of(2016,4,1);
        String firstName = "John";
        String lastName = "Doe";
        PayslipEntityPrimaryKey payslipEntityPrimaryKey1 = new PayslipEntityPrimaryKey(firstName, lastName, ld);
        PayslipEntityPrimaryKey payslipEntityPrimaryKey2 = new PayslipEntityPrimaryKey(firstName, lastName, ld2);

        assertNotSame(payslipEntityPrimaryKey1,payslipEntityPrimaryKey2);
        assertNotSame(payslipEntityPrimaryKey1.hashCode(),payslipEntityPrimaryKey2.hashCode());

    }


}
