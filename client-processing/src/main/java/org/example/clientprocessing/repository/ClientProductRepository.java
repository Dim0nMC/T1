package org.example.clientprocessing.repository;

import org.example.clientprocessing.model.ClientProduct;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientProductRepository extends JpaRepository<ClientProduct, Long> {
}
