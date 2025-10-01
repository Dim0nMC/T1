package org.example.accountprocessing.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.enums.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientPaymentMessageDTO {

    private Long accountId;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private Boolean isCredit;
    private LocalDateTime payedAt;
    private PaymentType type;
    private Boolean expired;
}
