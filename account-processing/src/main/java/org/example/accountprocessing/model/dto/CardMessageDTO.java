package org.example.accountprocessing.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardMessageDTO {
    private Long accountId;
    private String cardId;
    private String paymentSystem;
}
