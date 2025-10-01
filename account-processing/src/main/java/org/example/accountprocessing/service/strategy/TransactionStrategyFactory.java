package org.example.accountprocessing.service.strategy;

import org.example.accountprocessing.model.dto.ClientTransactionMessageDTO;
import org.example.accountprocessing.model.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TransactionStrategyFactory {

    private final Map<String, TransactionStrategy> strategies;

    public TransactionStrategyFactory(
            DepositRegularStrategy depositRegular,
            WithdrawRegularStrategy withdrawRegular,
            DepositCreditStrategy depositCredit,
            WithdrawCreditStrategy withdrawCredit
    ) {
        this.strategies = Map.of(
                "DEPOSIT_REGULAR", depositRegular,
                "WITHDRAW_REGULAR", withdrawRegular,
                "DEPOSIT_CREDIT", depositCredit,
                "WITHDRAW_CREDIT", withdrawCredit
        );
    }

    public TransactionStrategy getStrategy(TransactionType type, boolean isRecalc) {
        String key = type.toString() + "_" + (isRecalc ? "CREDIT" : "REGULAR");
        return strategies.get(key);
    }
}
