package org.example.accountprocessing.repository;

import org.example.accountprocessing.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
