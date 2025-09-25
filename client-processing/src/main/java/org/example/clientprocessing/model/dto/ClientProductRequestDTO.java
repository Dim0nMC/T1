package org.example.clientprocessing.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientProductRequestDTO {
    private Long clientId;
    private Long productId;
    private String status;
}
