package org.example.accountprocessing.service;

import org.example.accountprocessing.mapper.TransactionMapper;
import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Transaction;
import org.example.accountprocessing.model.dto.ClientTransactionMessageDTO;
import org.example.accountprocessing.model.dto.TransactionDTO;
import org.example.accountprocessing.model.enums.AccountStatus;
import org.example.accountprocessing.model.enums.TransactionStatus;
import org.example.accountprocessing.repository.AccountRepository;
import org.example.accountprocessing.repository.TransactionRepository;
import org.example.accountprocessing.service.strategy.TransactionStrategy;
import org.example.accountprocessing.service.strategy.TransactionStrategyFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class TransactionService {

    @Value("${transaction.t}")
    int T;
    @Value("${transaction.n}")
    int N;

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

        TransactionDTO dto = transactionMapper.toTransactionDTO(message);
        Transaction transaction = this.create(dto);

        if(account.getStatus() != AccountStatus.ACTIVE) {
            transaction.setStatus(TransactionStatus.BLOCKED);
            transactionRepository.save(transaction);
            System.out.println("Account is inactive: " + message.getAccountId());
        }


        LocalDateTime now = LocalDateTime.now();
        LocalDateTime periodStart = now.minusMinutes(T);
        long recentTxCount = transactionRepository.countByCardIdAndTimestampBetween(message.getCardId().toString(), periodStart, now);

        if (recentTxCount >= N) {
            account.setStatus(AccountStatus.BLOCKED);
            accountRepository.save(account);

            transaction.setStatus(TransactionStatus.BLOCKED);
            transactionRepository.save(transaction);

            System.out.println("Account " + account.getId() + " is blocked due to excessive transactions.");
            return;
        }


        try{

            transaction.setStatus(TransactionStatus.PROCESSING);
            transactionRepository.save(transaction);

            TransactionStrategy strategy = strategyFactory.getStrategy(message.getType(), account.getIsRecalc());
            strategy.process(transaction, account);

            transaction.setStatus(TransactionStatus.COMPLETE);
            transactionRepository.save(transaction);
        }
        catch(RuntimeException ex) {
            transaction.setStatus(TransactionStatus.CANCELLED);
            transactionRepository.save(transaction);
            System.out.println("Error processing transaction: "+transaction.getId()+" Reason: "+ex.getMessage());
        }

    }
}
