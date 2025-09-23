package org.example.clientprocessing.repository;

import org.example.clientprocessing.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
