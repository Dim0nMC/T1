package org.example.clientprocessing.repository;

import org.example.clientprocessing.model.BlacklistRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRegistryRepository extends JpaRepository<BlacklistRegistry, Long> {
}
