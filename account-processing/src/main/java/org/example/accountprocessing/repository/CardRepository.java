package org.example.accountprocessing.repository;

import org.example.accountprocessing.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
