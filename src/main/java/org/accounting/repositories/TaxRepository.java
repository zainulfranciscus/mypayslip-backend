package org.accounting.repositories;

import org.accounting.model.tax.impl.TaxEntity;
import org.accounting.model.tax.impl.TaxPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TaxRepository extends JpaRepository<TaxEntity, TaxPrimaryKey> {

    @Query(value = "select t from TaxEntity t where t.primaryKey.minTaxableIncome <= ?1 and (t.primaryKey.maxTaxableIncome >= ?1 or t.primaryKey.maxTaxableIncome = -99999999)")
    TaxEntity findTaxForEmployeeWithIncome(BigDecimal income);

}
