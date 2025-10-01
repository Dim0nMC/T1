package org.example.accountprocessing.service;

import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Payment;
import org.example.accountprocessing.model.enums.PaymentType;
import org.example.accountprocessing.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {


    private BigDecimal interestRate;
    private int monthCount;
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          @Value("${credit.interest-rate}")BigDecimal interestRate,
                          @Value("${credit.month-count}")int monthCount) {
        this.paymentRepository = paymentRepository;
        this.interestRate = interestRate;
        this.monthCount = monthCount;
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
            payment.setType(PaymentType.DEPOSIT);
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

    public Payment update(Payment payment){
        return paymentRepository.save(payment);
    }
}
