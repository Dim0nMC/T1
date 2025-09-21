package org.example.creditprocessing.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_registry")
public class ProductRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clientId;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private Long productId;

    @Column(precision = 5, scale = 2)
    private BigDecimal interestRate;

    private LocalDate openDate;

    @OneToMany(mappedBy = "productRegistry", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PaymentRegistry> payments;
}
