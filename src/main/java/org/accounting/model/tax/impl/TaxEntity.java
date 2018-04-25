package org.accounting.model.tax.impl;

import org.accounting.model.tax.Tax;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TAX")
@Validated
public class TaxEntity implements Tax {
    public static final BigDecimal NO_MAX_INCOME = new BigDecimal(-99999999);

    @EmbeddedId
    private TaxPrimaryKey primaryKey;

    @TaxValidator
    @NotNull
    private BigDecimal baseTax;


    @TaxValidator
    @NotNull
    private BigDecimal taxPerDollarInCents;

    @TaxValidator
    @NotNull
    private BigDecimal taxPerDollarOver;

    public TaxEntity() {
        this.primaryKey = new TaxPrimaryKey(new BigDecimal(0), new BigDecimal(0));
        this.taxPerDollarOver = new BigDecimal(0);
        this.taxPerDollarInCents = new BigDecimal(0);
        this.baseTax = new BigDecimal(0);

    }

    public TaxEntity(BigDecimal taxPerDollarOver,
                     BigDecimal taxPerDollarInCents,
                     BigDecimal baseTax,
                     BigDecimal minIncome,
                     BigDecimal maxIncome,
                     LocalDate startPeriod) {

        this.primaryKey = new TaxPrimaryKey(minIncome, maxIncome);
        this.taxPerDollarOver = taxPerDollarOver;
        this.taxPerDollarInCents = taxPerDollarInCents;
        this.baseTax = baseTax;
    }

    @TaxValidator
    public BigDecimal getMinTaxableIncome() {
        return this.primaryKey.getMinTaxableIncome();
    }

    @TaxValidator
    public BigDecimal getMaxTaxableIncome() {
        return this.primaryKey.getMaxTaxableIncome();
    }


    public BigDecimal getTaxPerDollarInCents() {
        return taxPerDollarInCents;
    }

    public BigDecimal getBaseTax() {
        return baseTax;
    }

    public BigDecimal getTaxPerDollarOver() {
        return this.taxPerDollarOver;
    }

}
