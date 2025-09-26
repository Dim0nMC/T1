package org.example.clientprocessing.model.dto;

import lombok.*;
import org.example.clientprocessing.model.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientProductRequestDTO {
    private Long clientId;
    private Long productId;
    private Status status;
    private LocalDate openDate;
    private LocalDate closeDate;

    private BigDecimal amount;
    private Long accountId;
    private Long monthCount;
}
