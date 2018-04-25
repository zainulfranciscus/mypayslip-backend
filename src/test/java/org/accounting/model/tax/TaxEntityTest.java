package org.accounting.model.tax;

import org.accounting.model.tax.impl.TaxEntity;
import org.accounting.model.tax.impl.TaxPrimaryKey;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
import static org.accounting.model.tax.impl.TaxEntity.NO_MAX_INCOME;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaxEntityTest {

    @Autowired
    private TestEntityManager em;
    private TaxBuilder taxBuilder;
    private static final BigDecimal NEGATIVE_NUMBER = new BigDecimal(-1);
    private BigDecimal maxIncome = new BigDecimal(25000);
    private BigDecimal minIncome = new BigDecimal(20000);

    @Test
    public void testGetByPrimaryKey_shouldReturnTaxWithMatchingAttributes(){

        taxBuilder = new TaxBuilder();
        BigDecimal baseTax = new BigDecimal(2000);
        BigDecimal taxPerDollar = new BigDecimal(100);
        BigDecimal taxPerDollarOver = new BigDecimal(20);

        int year = 2018;
        int month = 03;
        int date = 1;

        taxBuilder.withBaseTax(baseTax);
        taxBuilder.withTaxPerDollar(taxPerDollar);
        taxBuilder.withTaxPerDollarOver(taxPerDollarOver);
        taxBuilder.withMaxIncome(maxIncome);
        taxBuilder.withMinIncome(minIncome);
        taxBuilder.withStartPeriod(year, month, date);
        TaxEntity taxEntity = taxBuilder.build();

        em.persist(taxEntity);

        TaxEntity t = em.find(TaxEntity.class, new TaxPrimaryKey(minIncome,maxIncome));
        assertEquals(baseTax, t.getBaseTax());
        assertEquals(taxPerDollar,t.getTaxPerDollarInCents());
        assertEquals(taxPerDollarOver,t.getTaxPerDollarOver());
        assertEquals(maxIncome,t.getMaxTaxableIncome());
        assertEquals(minIncome,t.getMinTaxableIncome());


    }

    @Test
    public void shouldThrowException_whenBaseTaxIsNegative(){
        taxBuilder = new TaxBuilder();
        taxBuilder.withBaseTax(NEGATIVE_NUMBER);
        TaxEntity t = taxBuilder.build();

        assertThatConstraintViolationExceptionHappenned(t);

    }

    @Test
    public void shouldThrowException_whenMinIncomeIsNegative(){
        taxBuilder = new TaxBuilder();
        taxBuilder.withMinIncome(NEGATIVE_NUMBER);
        TaxEntity t = taxBuilder.build();

        assertThatConstraintViolationExceptionHappenned(t);

    }

    @Test
    public void shouldThrowException_whenMaxIncomeIsNegative(){
        taxBuilder = new TaxBuilder();
        taxBuilder.withMaxIncome(NEGATIVE_NUMBER);
        TaxEntity t = taxBuilder.build();

        assertThatConstraintViolationExceptionHappenned(t);

    }

    @Test
    public void shouldNotThrowException_whenMaxIncomeIsEqualToNoMaxIncome(){
        taxBuilder = new TaxBuilder();
        taxBuilder.withMinIncome(minIncome);
        taxBuilder.withMaxIncome(NO_MAX_INCOME);
        TaxEntity taxEntity = taxBuilder.build();
        em.persist(taxEntity);

        TaxEntity t = em.find(TaxEntity.class, new TaxPrimaryKey(minIncome,NO_MAX_INCOME));
        assertEquals(NO_MAX_INCOME,t.getMaxTaxableIncome());
    }

    @Test
    public void shouldThrowException_whenTaxPerDollarIsNegative(){
        taxBuilder = new TaxBuilder();
        taxBuilder.withTaxPerDollarOver(NEGATIVE_NUMBER);
        TaxEntity t = taxBuilder.build();

        assertThatConstraintViolationExceptionHappenned(t);




    }

    @Test
    public void shouldThrowException_whenTaxPerDollarOverIsNegative(){
        taxBuilder = new TaxBuilder();
        taxBuilder.withTaxPerDollarOver(NEGATIVE_NUMBER);
        TaxEntity t = taxBuilder.build();

        assertThatConstraintViolationExceptionHappenned(t);

    }



    private void assertThatConstraintViolationExceptionHappenned(TaxEntity t){
        Executable executable = () -> {
            em.persist(t);
            em.flush();
        };
        assertThrows(ConstraintViolationException.class, executable);
    }
}
