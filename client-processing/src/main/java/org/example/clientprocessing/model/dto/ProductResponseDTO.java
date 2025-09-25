package org.example.clientprocessing.model.dto;

import lombok.*;
import org.example.clientprocessing.model.enums.Key;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Key key;
    private LocalDateTime createDate;
    private String productId;
}
