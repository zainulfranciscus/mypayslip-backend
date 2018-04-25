package org.accounting.model.tax.impl;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
public class TaxPrimaryKey implements Serializable {

    @TaxValidator
    @NotNull
    private BigDecimal minTaxableIncome = new BigDecimal(0);

    @TaxValidator
    @NotNull
    private BigDecimal maxTaxableIncome = new BigDecimal(0);

    public TaxPrimaryKey() {

    }

    public TaxPrimaryKey(BigDecimal minIncome, BigDecimal maxIncome) {
        minTaxableIncome = minIncome;
        maxTaxableIncome = maxIncome;
    }

    public BigDecimal getMinTaxableIncome() {
        return minTaxableIncome;
    }

    public BigDecimal getMaxTaxableIncome() {
        return maxTaxableIncome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TaxPrimaryKey that = (TaxPrimaryKey) o;

        return new EqualsBuilder()
                .append(minTaxableIncome, that.minTaxableIncome)
                .append(maxTaxableIncome, that.maxTaxableIncome)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(minTaxableIncome)
                .append(maxTaxableIncome)
                .toHashCode();
    }
}
