package org.example.accountprocessing.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.accountprocessing.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private Long accountId;
    private Long cardId;

    private TransactionType type;
    private BigDecimal amount;

    private LocalDateTime timestamp;
}
