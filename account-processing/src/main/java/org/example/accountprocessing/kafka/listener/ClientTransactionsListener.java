package org.example.accountprocessing.kafka.listener;

import org.example.accountprocessing.model.dto.ClientTransactionMessageDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ClientTransactionsListener {

    @KafkaListener(topics = "client_transactions", groupId = "ms2-group")
    public void listen(ClientTransactionMessageDTO message) {

    }
}
