package org.accounting.controller.response;

import org.accounting.model.tax.Tax;

public class TaxResponse extends Response<Tax>{

    private Tax tax;

    public TaxResponse(){
        super();
    }

    public TaxResponse(Tax tax, int status, String message) {
        super(status,message);
        this.tax = tax;
    }

    @Override
    public Tax getResponseObject() {
        return tax;
    }



}
