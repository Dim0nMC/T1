package org.example.clientprocessing.model;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.clientprocessing.model.enums.DocumentType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blacklist_registry")
public class BlacklistRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private DocumentType documentType;

    @Column(length = 100, nullable = false)
    private String documentId;

    @Column(nullable = false)
    private LocalDateTime blacklistedAt;

    @Column(length = 255)
    private String reason;

    private LocalDate blacklistExpirationDate;

}
