package org.example.accountprocessing.service;

import org.example.accountprocessing.mapper.PaymentMapper;
import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Payment;
import org.example.accountprocessing.model.dto.ClientPaymentMessageDTO;
import org.example.accountprocessing.model.enums.PaymentType;
import org.example.accountprocessing.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {


    private BigDecimal interestRate;
    private int monthCount;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final AccountService accountService;

    public PaymentService(PaymentRepository paymentRepository,
                          @Value("${credit.interest-rate}")BigDecimal interestRate,
                          @Value("${credit.month-count}")int monthCount,
                          PaymentMapper paymentMapper,
                          AccountService accountService) {
        this.paymentRepository = paymentRepository;
        this.interestRate = interestRate;
        this.monthCount = monthCount;
        this.paymentMapper = paymentMapper;
        this.accountService = accountService;
    }

    public void generatePaymentSchedule(LocalDate openDate, BigDecimal loanAmount, Account account) {

        // Месячная процентная ставка
        BigDecimal monthlyRate = interestRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        // Вычисляем аннуитетный платёж
        BigDecimal onePlusRate = BigDecimal.ONE.add(monthlyRate);
        BigDecimal pow = onePlusRate.pow(monthCount);
        BigDecimal numerator = loanAmount.multiply(monthlyRate).multiply(pow);
        BigDecimal denominator = pow.subtract(BigDecimal.ONE);
        BigDecimal annuityPayment = numerator.divide(denominator, 2, RoundingMode.HALF_UP);

        for (int month = 1; month <= monthCount; month++) {
            Payment payment = new Payment();
            payment.setAmount(annuityPayment);
            payment.setPaymentDate(openDate.plusMonths(month - 1));
            payment.setType(PaymentType.WITHDRAW);
            payment.setIsCredit(true);
            payment.setAccount(account);

            paymentRepository.save(payment);
        }
    }

    public Payment getPaymentForCurrentMonth(Long accountId) {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        return paymentRepository.findPaymentForCurrentMonth(accountId, year, month);
    }

    public List<Payment> findExpiredWithdrawByAccountId(Long accountId) {
        return paymentRepository.findExpiredWithdrawByAccountId(accountId);
    }

    public Payment update(Payment payment){
        return paymentRepository.save(payment);
    }

    public void pay(ClientPaymentMessageDTO message){


        //суммируем все просроченные платежи
        List<Payment> payments = findExpiredWithdrawByAccountId(message.getAccountId());
        BigDecimal totalAmount  = payments
                .stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        if(totalAmount .compareTo(message.getAmount()) == 0){

            Account account = accountService.getById(message.getAccountId());
            account.setBalance(account.getBalance().add(message.getAmount()));
            accountService.update(account);

            Payment newPayment = paymentMapper.toEntity(message);
            newPayment.setType(PaymentType.DEPOSIT);
            paymentRepository.save(newPayment);

            payments.forEach(payment -> payment.setPayedAt(LocalDateTime.now()));
            paymentRepository.saveAll(payments);

        }
    }
}
