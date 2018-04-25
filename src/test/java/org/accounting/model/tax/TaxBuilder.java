package org.accounting.model.tax;

import org.accounting.model.tax.impl.TaxEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TaxBuilder {

    private BigDecimal minIncome = new BigDecimal(0);
    private BigDecimal maxIncome = new BigDecimal(0);
    private BigDecimal taxPerDollar = new BigDecimal(0);
    private BigDecimal baseTax = new BigDecimal(0);
    private BigDecimal taxPerDollarOver = new BigDecimal(0);
    private LocalDate startPeriod = LocalDate.now();



    public TaxEntity build() {
        return new TaxEntity(taxPerDollarOver,
                taxPerDollar,
                baseTax,
                minIncome,
                maxIncome,
                startPeriod);
    }

    public TaxBuilder withMaxIncome(BigDecimal maxIncome) {
        this.maxIncome = maxIncome;
        return this;
    }

    public TaxBuilder withTaxPerDollar(BigDecimal taxPerDollar) {
        this.taxPerDollar =  taxPerDollar;
        return this;
    }

    public TaxBuilder withBaseTax(BigDecimal baseTax) {
        this.baseTax =  baseTax;
        return this;
    }

    public TaxBuilder withStartPeriod(int year, int month, int date){
        this.startPeriod = LocalDate.of(year,month,date);
        return  this;
    }

    public TaxBuilder withTaxPerDollarOver(BigDecimal taxPerDollarOver) {
        this.taxPerDollarOver = taxPerDollarOver;
        return this;
    }

    public TaxBuilder withMinIncome(BigDecimal minIncome) {
        this.minIncome = minIncome;
        return this;
    }


}
