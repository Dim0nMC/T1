package org.example.clientprocessing.model.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientProductResponseDTO {
    private Long id;
    private Long clientId;
    private Long productId;
    private String status;
    private LocalDate openDate;
    private LocalDate closeDate;
}
