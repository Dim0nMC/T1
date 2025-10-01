package org.example.accountprocessing.service.strategy;

import jakarta.transaction.Transactional;
import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Payment;
import org.example.accountprocessing.model.Transaction;
import org.example.accountprocessing.model.enums.TransactionStatus;
import org.example.accountprocessing.repository.AccountRepository;
import org.example.accountprocessing.repository.PaymentRepository;
import org.example.accountprocessing.repository.TransactionRepository;
import org.example.accountprocessing.service.PaymentService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DepositCreditStrategy implements TransactionStrategy {

    private final TransactionRepository transactionRepository;
    private final PaymentService paymentService;
    private final AccountRepository accountRepository;

    public DepositCreditStrategy(final TransactionRepository transactionRepository,
                                 final PaymentService paymentService,
                                 final AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.paymentService = paymentService;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public void process(Transaction transaction, Account account) {
        try {
            Payment actualPayment = paymentService.getPaymentForCurrentMonth(account.getId());

            if(transaction.getTimestamp().toLocalDate().isAfter( actualPayment.getPaymentDate())){

                if(account.getBalance().compareTo(transaction.getAmount())>=0){
                    account.setBalance(account.getBalance().subtract(transaction.getAmount()));
                    accountRepository.save(account);
                    actualPayment.setPayedAt(LocalDateTime.now());
                    paymentService.update(actualPayment);
                }
                else {
                    actualPayment.setExpired(true);
                    paymentService.update(actualPayment);
                    throw new RuntimeException("Insufficient funds on the balance");
                }

            }
            else {
                throw new RuntimeException("Payment date has not yet arrived");
            }

        } catch (RuntimeException ex) {
            throw ex;
        }
    }
}
