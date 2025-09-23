package org.example.clientprocessing.repository;

import org.example.clientprocessing.model.BlacklistRegistry;
import org.example.clientprocessing.model.enums.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRegistryRepository extends JpaRepository<BlacklistRegistry, Long> {

    Boolean existsByDocument_TypeAndDocument_Id(DocumentType documentType, String documentId);
}
