package org.example.accountprocessing.service.strategy;

import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Transaction;
import org.example.accountprocessing.model.enums.TransactionStatus;
import org.example.accountprocessing.repository.AccountRepository;
import org.example.accountprocessing.repository.TransactionRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class WithdrawRegularStrategy implements TransactionStrategy {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public WithdrawRegularStrategy(TransactionRepository transactionRepository,
                                  AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public void process(Transaction transaction, Account account) {
        try {
            transaction.setStatus(TransactionStatus.PROCESSING);
            transactionRepository.save(transaction);

            account.setBalance(account.getBalance().subtract(transaction.getAmount()));
            accountRepository.save(account);

            transaction.setStatus(TransactionStatus.COMPLETE);
            transactionRepository.save(transaction);

        } catch (RuntimeException ex) {
            transaction.setStatus(TransactionStatus.CANCELLED);
            transactionRepository.save(transaction);

            throw ex;
        }
    }
}
