package org.example.accountprocessing.repository;

import org.example.accountprocessing.model.Payment;
import org.example.accountprocessing.model.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p " +
            "WHERE p.account.id = :accountId " +
            "AND FUNCTION('YEAR', p.paymentDate) = :year " +
            "AND FUNCTION('MONTH', p.paymentDate) = :month")
    Payment findPaymentForCurrentMonth(@Param("accountId") Long accountId,
                                       @Param("year") int year,
                                       @Param("month") int month);

    @Query("SELECT p " +
            "FROM Payment p " +
            "WHERE p.account.id = :accountId " +
            "AND p.type = org.example.accountprocessing.model.enums.PaymentType.WITHDRAW " +
            "AND p.expired = true " +
            "AND p.payedAt IS NULL ")
    List<Payment> findExpiredWithdrawByAccountId(@Param("accountId") Long accountId);

}
