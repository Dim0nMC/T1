package org.example.accountprocessing.repository;

import org.example.accountprocessing.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface    TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.card.id = :cardId AND t.timestamp >= :from AND t.timestamp <= :to")
    long countByCardIdAndTimestampBetween(@Param("cardId") String cardId,
                                          @Param("from") LocalDateTime from,
                                          @Param("to") LocalDateTime to);
}
