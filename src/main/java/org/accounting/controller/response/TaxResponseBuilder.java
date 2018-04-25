package org.accounting.controller.response;

import org.accounting.model.tax.Tax;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class TaxResponseBuilder {

    private HttpStatus httpStatus;
    private Tax tax;
    private String message;

    public TaxResponseBuilder withOKHttpStatus(){
        this.httpStatus = OK;
        return this;
    }

    public TaxResponseBuilder withTax(Tax t){
        tax = t;
        return this;
    }

    public TaxResponseBuilder withMessage(String m){
        this.message = m;
        return this;
    }

    public TaxResponseBuilder withBadRequestStatus(){
        this.httpStatus = BAD_REQUEST;
        return this;
    }
    public TaxResponse build(){
        TaxResponse taxResponse = new TaxResponse(tax,httpStatus.value(),message);
        return taxResponse;
    }
}
