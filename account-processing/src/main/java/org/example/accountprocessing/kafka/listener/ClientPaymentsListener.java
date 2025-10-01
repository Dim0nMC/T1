package org.example.accountprocessing.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.accountprocessing.model.dto.ClientPaymentMessageDTO;
import org.example.accountprocessing.model.dto.ClientTransactionMessageDTO;
import org.example.accountprocessing.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ClientPaymentsListener {

    private final PaymentService paymentService;

    public ClientPaymentsListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "client_payments", groupId = "ms2-group")
    public void listen(ConsumerRecord<String, ClientPaymentMessageDTO> record) {
        String key = record.key();
        ClientPaymentMessageDTO value = record.value();

        paymentService.pay(value);
    }

}
