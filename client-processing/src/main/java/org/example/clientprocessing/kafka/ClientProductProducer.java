package org.example.clientprocessing.kafka;

import lombok.RequiredArgsConstructor;
import org.example.clientprocessing.model.dto.ClientProductCreditMessageDTO;
import org.example.clientprocessing.model.dto.ClientProductMessageDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientProductProducer {

    private final KafkaTemplate<String, ClientProductMessageDTO> kafkaTemplate;
    private final KafkaTemplate<String, ClientProductCreditMessageDTO> creditKafkaTemplate;

    public void sendToClientProducts(ClientProductMessageDTO message) {
        kafkaTemplate.send("client_products", message);
    }

    public void sendToClientCreditProducts(ClientProductCreditMessageDTO message) {
        creditKafkaTemplate.send("client_credit_products", message);
    }
}
