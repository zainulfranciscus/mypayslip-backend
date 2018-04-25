package org.accounting.model.tax;

import org.accounting.model.tax.impl.TaxPrimaryKey;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

public class TaxPrimaryKeyTest {

    private TaxPrimaryKey pk1 = new TaxPrimaryKey(new BigDecimal(0),new BigDecimal(1));
    private TaxPrimaryKey pk2 = new TaxPrimaryKey(new BigDecimal(0),new BigDecimal(1));
    private TaxPrimaryKey pk3 = new TaxPrimaryKey(new BigDecimal(2),new BigDecimal(3));

    @Test
    public void taxPK_HasEqualHashCode_WhenMinAndMaxIncome_isEqual(){
        assertEquals(pk1.hashCode(),pk2.hashCode());
    }

    @Test
    public void taxPK_HasDifferentHashCode_WhenMinAndMaxIncome_isDifferent(){
        assertNotSame(pk1.hashCode(),pk3.hashCode());
    }

    @Test
    public void taxPK_AreEqual_WhenMinAndMaxIncome_isEqual(){
        assertTrue(pk1.equals(pk2));
    }

    @Test
    public void taxPK_AreNotEqual_WhenMinAndMaxIncome_isDifferent(){
        assertFalse(pk1.equals(pk3));
    }
}
