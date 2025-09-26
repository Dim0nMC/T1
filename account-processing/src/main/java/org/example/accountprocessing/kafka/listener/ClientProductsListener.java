package org.example.accountprocessing.kafka.listener;

import org.example.accountprocessing.model.dto.ClientProductMessageDTO;
import org.example.accountprocessing.service.AccountService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ClientProductsListener {

    private final AccountService accountService;

    public ClientProductsListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @KafkaListener(topics = "client_products", groupId = "ms2-group")
    public void listen(ClientProductMessageDTO message) {
        //System.out.println("Received message: " + message);
        accountService.createAccount(message);
    }
}

