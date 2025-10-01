package org.example.accountprocessing.service;

import org.example.accountprocessing.mapper.TransactionMapper;
import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Transaction;
import org.example.accountprocessing.model.dto.ClientTransactionMessageDTO;
import org.example.accountprocessing.model.dto.TransactionDTO;
import org.example.accountprocessing.model.enums.AccountStatus;
import org.example.accountprocessing.repository.AccountRepository;
import org.example.accountprocessing.repository.TransactionRepository;
import org.example.accountprocessing.service.strategy.TransactionStrategy;
import org.example.accountprocessing.service.strategy.TransactionStrategyFactory;

public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionStrategyFactory strategyFactory;
    private final TransactionMapper transactionMapper;

    public TransactionService(AccountRepository accountRepository,
                              TransactionStrategyFactory strategyFactory,
                              TransactionMapper transactionMapper,
                              TransactionRepository transactionRepository) {

        this.accountRepository = accountRepository;
        this.strategyFactory = strategyFactory;
        this.transactionMapper = transactionMapper;
        this.transactionRepository = transactionRepository;
    }

    public Transaction create(TransactionDTO dto) {
        Transaction transaction = transactionMapper.toEntity(dto);
        return transactionRepository.save(transaction);
    }

    public void makeTransaction(ClientTransactionMessageDTO message) {
        Account account = accountRepository.getById(message.getAccountId());
        TransactionStrategy strategy = strategyFactory.getStrategy(message.getType(), account.getIsRecalc());

        if(account.getStatus() == AccountStatus.ACTIVE) {
            TransactionDTO dto = transactionMapper.toTransactionDTO(message);
            Transaction transaction = this.create(dto);
            try{
                strategy.process(transaction, account);
            }
            catch(RuntimeException ex) {
                System.out.println("error processing transaction: "+transaction.getId());
            }

        }
        else {
            System.out.println("Account is inactive: " + message.getAccountId());
        }
    }
}
