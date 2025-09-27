package org.example.creditprocessing.service;

import org.example.creditprocessing.repository.PaymentRegistryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreditDecisionService {
    private final PaymentRegistryRepository paymentRegistryRepository;
    private final BigDecimal creditLimit;

    public CreditDecisionService(PaymentRegistryRepository paymentRegistryRepository,
                                 @Value("${credit.limit}") BigDecimal creditLimit) {
        this.paymentRegistryRepository = paymentRegistryRepository;
        this.creditLimit = creditLimit;
    }

    public boolean approveCredit(Long clientId, BigDecimal newAmount) {
        // «Суммарная задолженность по продуктам» = сумма debt_amount по всем платежам продукта, которые ещё не оплачены (payment_date IS NULL)
        BigDecimal currentSum = paymentRegistryRepository.sumDebtByClientId(clientId);

        // проверка по лимиту
        if (currentSum.add(newAmount).compareTo(creditLimit) > 0) {
            return false;
        }
        // проверка на просрочки
        boolean hasExpired = paymentRegistryRepository.existsByClientIdAndExpiredTrue(clientId);
        if (hasExpired) {
            return false;
        }

        return true;
    }
}
