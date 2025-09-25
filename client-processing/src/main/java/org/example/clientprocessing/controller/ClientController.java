package org.example.clientprocessing.controller;

import jakarta.validation.Valid;
import org.example.clientprocessing.model.Client;
import org.example.clientprocessing.model.User;
import org.example.clientprocessing.model.dto.ClientRegistrationDTO;
import org.example.clientprocessing.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody ClientRegistrationDTO dto) {

        User user = clientService.register(dto);

        return ResponseEntity.ok(user);
    }

}
