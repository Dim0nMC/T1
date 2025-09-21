package org.example.creditprocessing.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_registry")
public class PaymentRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ProductRegistry productRegistry;

    private LocalDate paymentDate;

    @Column( precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(precision = 19, scale = 2)
    private BigDecimal interestRateAmount;

    @Column(precision = 19, scale = 2)
    private BigDecimal debtAmount;

    private Boolean expired = false;

    private LocalDate paymentExpirationDate;
}
