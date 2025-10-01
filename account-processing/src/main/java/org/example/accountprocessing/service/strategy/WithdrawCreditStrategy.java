package org.example.accountprocessing.service.strategy;

import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Transaction;
import org.example.accountprocessing.model.enums.TransactionStatus;
import org.example.accountprocessing.repository.TransactionRepository;
import org.example.accountprocessing.service.PaymentService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class WithdrawCreditStrategy implements TransactionStrategy {

    private final TransactionRepository transactionRepository;
    private final PaymentService paymentService;

    public WithdrawCreditStrategy(TransactionRepository transactionRepository,
                                  PaymentService paymentService){

        this.transactionRepository = transactionRepository;
        this.paymentService = paymentService;
    }

    @Override
    @Transactional
    public void process(Transaction transaction, Account account) {
        try {
            paymentService.generatePaymentSchedule(transaction.getTimestamp().toLocalDate(), transaction.getAmount(), account);

        } catch (RuntimeException ex) {
            throw ex;
        }
    }
}
