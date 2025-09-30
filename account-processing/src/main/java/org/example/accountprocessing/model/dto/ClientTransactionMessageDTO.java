package org.example.accountprocessing.model.dto;

import jakarta.persistence.*;
import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Card;
import org.example.accountprocessing.model.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ClientTransactionMessageDTO {

    private Account accountId;
    private Card cardId;

    private String type;
    private BigDecimal amount;

    private LocalDateTime timestamp;
}
