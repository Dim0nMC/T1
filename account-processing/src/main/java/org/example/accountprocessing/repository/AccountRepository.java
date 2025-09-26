package org.example.accountprocessing.repository;

import org.example.accountprocessing.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
