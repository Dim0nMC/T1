package org.example.creditprocessing.kafka.listener;

import org.example.creditprocessing.model.dto.ClientProductCreditMessageDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ClientCreditProductsListener {

    @KafkaListener(topics = "client_credit_products", groupId = "ms3-group")
    public void listen(ClientProductCreditMessageDTO message) {

    }
}
