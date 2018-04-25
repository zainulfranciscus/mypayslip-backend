package org.accounting.controller.response;

import org.accounting.model.tax.Tax;
import org.accounting.model.tax.impl.TaxEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TaxResponseTest {

    @Test
    public void taxShouldBeNull(){
        TaxResponse taxResponse = new TaxResponse();
        assertNull(taxResponse.getResponseObject());
    }

    public void taxShouldBeEqual_toTheGivenTax(){
        Tax tax = new TaxEntity();
        TaxResponse taxResponse = new TaxResponse(tax,200,"no message");
        assertEquals(tax,taxResponse.getResponseObject());
    }
}
