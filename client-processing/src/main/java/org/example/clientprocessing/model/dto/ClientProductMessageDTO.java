package org.example.clientprocessing.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientProductMessageDTO {
    private Long clientId;
    private Long productId;
    private LocalDate openDate;
}
