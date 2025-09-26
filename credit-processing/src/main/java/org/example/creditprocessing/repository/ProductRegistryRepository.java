package org.example.creditprocessing.repository;

import org.example.creditprocessing.model.ProductRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRegistryRepository extends JpaRepository<ProductRegistry, Long> {
}
