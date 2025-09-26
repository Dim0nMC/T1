package org.example.clientprocessing.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientProductCreditMessageDTO {
    private Long clientId;
    private Long productId;
    private LocalDate openDate;

    private BigDecimal amount;
    private Long accountId;
    private Integer monthCount;
}
