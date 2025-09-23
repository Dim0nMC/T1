package org.example.clientprocessing.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.clientprocessing.model.enums.DocumentType;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRegistrationDTO {

    @NotBlank(message = "Login must not be empty")
    private String login;

    @NotBlank(message = "Password must not be empty")
    private String password;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email must not be empty")
    private String email;

    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private DocumentType documentType;
    private String documentId;
    private String documentPrefix;
    private String documentSuffix;

}
