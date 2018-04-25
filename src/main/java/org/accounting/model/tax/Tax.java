package org.accounting.model.tax;

import java.math.BigDecimal;

public interface Tax {

    BigDecimal getMinTaxableIncome();

    BigDecimal getMaxTaxableIncome();

    BigDecimal getTaxPerDollarInCents();

    BigDecimal getBaseTax();

    BigDecimal getTaxPerDollarOver();

}
