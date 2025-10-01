package org.example.accountprocessing.service.strategy;

import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Transaction;
import org.example.accountprocessing.model.dto.ClientTransactionMessageDTO;

public interface TransactionStrategy {
    void process(Transaction transaction, Account account);
}
