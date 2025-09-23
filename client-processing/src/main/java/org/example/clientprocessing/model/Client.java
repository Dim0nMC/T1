package org.example.clientprocessing.model;

import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;
import org.example.clientprocessing.model.enums.DocumentType;


import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(
            regexp = "^[0-9]{12}$",
            message = "Client ID должен состоять из 12 цифр в формате XXFFNNNNNNNN, где XX - номер региона, FF - номер подразделения банка, N - порядковый номер."
    )
    @Column(nullable = false, unique = true, length = 20)
    private String clientId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String middleName;

    @Column(length = 50)
    private String lastName;


    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DocumentType documentType;

    @Column(length = 50)
    private String documentId;

    @Column(length = 10)
    private String documentPrefix;

    @Column(length = 10)
    private String documentSuffix;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<ClientProduct> clientProducts;


}
