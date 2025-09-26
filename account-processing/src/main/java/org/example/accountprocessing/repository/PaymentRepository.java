package org.example.accountprocessing.repository;

import org.example.accountprocessing.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
