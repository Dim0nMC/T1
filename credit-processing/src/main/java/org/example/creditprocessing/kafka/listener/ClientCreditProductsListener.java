package org.example.creditprocessing.kafka.listener;

import org.example.creditprocessing.model.dto.ClientProductCreditMessageDTO;
import org.example.creditprocessing.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ClientCreditProductsListener {

    private final CreditService creditService;

    @Autowired
    public ClientCreditProductsListener(final CreditService creditService) {
        this.creditService = creditService;
    }

    @KafkaListener(topics = "client_credit_products", groupId = "ms3-group")
    public void listen(ClientProductCreditMessageDTO message) {
        creditService.addProduct(message);
    }
}
