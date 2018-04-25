package org.accounting.repositories;


import org.accounting.model.tax.impl.TaxEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Sql({"classpath:schema.sql","classpath:data-test.sql"})
@RunWith(SpringRunner.class)
@DataJpaTest
public class TaxEntityRepositoryTest {

    @Autowired
    private TaxRepository taxRepository;

    private BigDecimal maxIncome = new BigDecimal(18200.00);
    private BigDecimal minIncome = new BigDecimal(0);

    @Test
    public void shouldReturnThisTaxBracket_BecauseIncomeIsWithinTheMinIncome() {

        TaxEntity t = taxRepository.findTaxForEmployeeWithIncome(minIncome);
        assertEquals(t.getMaxTaxableIncome().stripTrailingZeros(), maxIncome.stripTrailingZeros());

    }

    @Test
    public void shouldReturnThisTaxBracket_BecauseIncomeIsWithinTheMaxIncome() {

        TaxEntity t = taxRepository.findTaxForEmployeeWithIncome(maxIncome);
        assertEquals(t.getMaxTaxableIncome().stripTrailingZeros(), maxIncome.stripTrailingZeros());

    }


    @Test
    public void shouldNotReturnAnyTaxBracket_becauseIncomeIsLessThanMinIncomeInTaxBracket() {
        TaxEntity t = taxRepository.findTaxForEmployeeWithIncome(minIncome.subtract(new BigDecimal(1)));
        assertNull(t);
    }

    @Test
    public void shouldReturnNoTax_BecauseIncomeIsMoreThanTheHighestTaxBracket(){
        BigDecimal income = new BigDecimal(5000000);
        TaxEntity t = taxRepository.findTaxForEmployeeWithIncome(income);
        assertNull(t);
    }

}

