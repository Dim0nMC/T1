package org.example.accountprocessing.service.strategy;

import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Transaction;
import org.example.accountprocessing.model.dto.ClientTransactionMessageDTO;
import org.springframework.stereotype.Component;

@Component
public class DepositCreditStrategy implements TransactionStrategy {

    @Override
    public void process(Transaction transaction, Account account) {}
}
