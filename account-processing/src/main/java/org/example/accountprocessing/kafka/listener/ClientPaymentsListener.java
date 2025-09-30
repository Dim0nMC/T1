package org.example.accountprocessing.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.accountprocessing.model.dto.ClientPaymentMessageDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ClientPaymentsListener {

    @KafkaListener(topics = "client_payments", groupId = "ms2-group")
    public void listen(ClientPaymentMessageDTO messageDTO) {

    }
}
