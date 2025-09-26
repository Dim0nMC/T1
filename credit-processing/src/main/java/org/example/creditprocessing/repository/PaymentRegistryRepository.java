package org.example.creditprocessing.repository;

import org.example.creditprocessing.model.PaymentRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface PaymentRegistryRepository extends JpaRepository<PaymentRegistry, Long> {

    @Query("SELECT COALESCE(SUM(p.debtAmount), 0) " +
            "FROM PaymentRegistry p " +
            "JOIN p.productRegistry pr " +
            "WHERE pr.clientId = :clientId " +
            "AND p.paymentDate IS NULL")
    BigDecimal sumDebtByClientId(@Param("clientId") Long clientId);

    @Query("""
           SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
           FROM PaymentRegistry p
           JOIN p.productRegistry pr
           WHERE pr.clientId = :clientId AND p.expired = true
           """)
    boolean existsByClientIdAndExpiredTrue(@Param("clientId") Long clientId);
}
