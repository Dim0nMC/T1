package org.example.creditprocessing.repository;

import org.example.creditprocessing.model.PaymentRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRegistryRepository extends JpaRepository<PaymentRegistry, Long> {
}
