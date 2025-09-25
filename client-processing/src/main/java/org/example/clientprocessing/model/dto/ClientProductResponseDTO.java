package org.example.clientprocessing.model.dto;

import lombok.*;
import org.example.clientprocessing.model.enums.Status;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientProductResponseDTO {
    private Long id;
    private Long clientId;
    private Long productId;
    private Status status;
    private LocalDate openDate;
    private LocalDate closeDate;
}
