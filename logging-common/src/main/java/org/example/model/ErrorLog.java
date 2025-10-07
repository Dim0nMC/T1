package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "error_log")
@Getter
@Setter
public class ErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    @Column(length = 500)
    private String methodSignature;

    @Column(length = 2000)
    private String exceptionMessage;

    @Column(columnDefinition = "TEXT")
    private String stackTrace;

    @Column(columnDefinition = "TEXT")
    private String methodArgs;
}
