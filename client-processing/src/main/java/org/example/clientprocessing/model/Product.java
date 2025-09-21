package org.example.clientprocessing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.clientprocessing.model.enums.Key;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Key key;

    private LocalDateTime createDate;

    @Column(length = 20)
    private String productId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ClientProduct> clientProducts;

    @PrePersist
    @PreUpdate
    private void generateProductId() {
        if (id != null && key != null) {
            this.productId = key + id.toString();
        }
    }

}
