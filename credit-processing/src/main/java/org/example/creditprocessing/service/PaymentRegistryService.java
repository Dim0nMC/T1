package org.example.creditprocessing.service;

import org.example.creditprocessing.model.PaymentRegistry;
import org.example.creditprocessing.model.ProductRegistry;
import org.example.creditprocessing.repository.PaymentRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class PaymentRegistryService {

    private final PaymentRegistryRepository paymentRegistryRepository;

    @Autowired
    public PaymentRegistryService(PaymentRegistryRepository paymentRegistryRepository) {
        this.paymentRegistryRepository = paymentRegistryRepository;
    }

    public void generatePaymentSchedule(ProductRegistry productRegistry, BigDecimal loanAmount) {

        BigDecimal annualInterestRate = productRegistry.getInterestRate(); // годовая ставка, например 0.22
        int monthCount = productRegistry.getMonthCount(); // количество месяцев
        LocalDate openDate = productRegistry.getOpenDate(); // дата открытия кредита

        // Месячная процентная ставка
        BigDecimal monthlyRate = annualInterestRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        // Вычисляем аннуитетный платёж
        BigDecimal onePlusRate = BigDecimal.ONE.add(monthlyRate);
        BigDecimal pow = onePlusRate.pow(monthCount);
        BigDecimal numerator = loanAmount.multiply(monthlyRate).multiply(pow);
        BigDecimal denominator = pow.subtract(BigDecimal.ONE);
        BigDecimal annuityPayment = numerator.divide(denominator, 2, RoundingMode.HALF_UP);

        BigDecimal remainingDebt = loanAmount;

        for (int month = 1; month <= monthCount; month++) {
            BigDecimal interestPart = remainingDebt.multiply(monthlyRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal principalPart = annuityPayment.subtract(interestPart).setScale(2, RoundingMode.HALF_UP);
            remainingDebt = remainingDebt.subtract(principalPart).setScale(2, RoundingMode.HALF_UP);

            PaymentRegistry payment = new PaymentRegistry();
            payment.setProductRegistry(productRegistry);
            payment.setPaymentDate(openDate.plusMonths(month - 1));
            payment.setAmount(annuityPayment);
            payment.setInterestRateAmount(interestPart);
            payment.setDebtAmount(principalPart);
            payment.setExpired(false);
            payment.setPaymentExpirationDate(openDate.plusMonths(month));

            paymentRegistryRepository.save(payment);
        }
    }
}



