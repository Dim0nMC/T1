package org.example.clientprocessing.repository;

import org.example.clientprocessing.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
