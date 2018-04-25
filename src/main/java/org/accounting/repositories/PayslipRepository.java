package org.accounting.repositories;

import org.accounting.model.payslip.impl.PayslipEntity;
import org.accounting.model.payslip.impl.PayslipEntityPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PayslipRepository extends JpaRepository<PayslipEntity, PayslipEntityPrimaryKey> {
    @Query("SELECT p FROM PayslipEntity p WHERE UPPER(p.primaryKey.employeeFirstName)=UPPER(:firstName) and UPPER(p.primaryKey.employeeLastName)=UPPER(:lastName) and month(p.primaryKey.payDate)=:paymentMonth")
    PayslipEntity findMatchingPayslips(@Param("firstName") String firstName, @Param("lastName")  String lastName, @Param("paymentMonth") int paymentMonth);
}
