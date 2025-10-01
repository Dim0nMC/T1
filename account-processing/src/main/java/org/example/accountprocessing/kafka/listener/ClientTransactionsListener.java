package org.example.accountprocessing.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.accountprocessing.model.dto.ClientTransactionMessageDTO;
import org.example.accountprocessing.service.TransactionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ClientTransactionsListener {

    private final TransactionService transactionService;

    public ClientTransactionsListener(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "client_transactions", groupId = "ms2-group")
    public void listen(ConsumerRecord<String, ClientTransactionMessageDTO> record) {
        String key = record.key();
        ClientTransactionMessageDTO value = record.value();
        transactionService.makeTransaction(value);

    }
}
