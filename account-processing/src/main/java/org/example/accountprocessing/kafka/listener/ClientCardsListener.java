package org.example.accountprocessing.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.accountprocessing.exception.AccountInactiveException;
import org.example.accountprocessing.model.Card;
import org.example.accountprocessing.model.dto.CardMessageDTO;
import org.example.accountprocessing.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ClientCardsListener {

    private final CardService cardService;

    @Autowired
    public ClientCardsListener(CardService cardService) {
        this.cardService = cardService;
    }

    @KafkaListener(topics = "client_cards", groupId = "ms2-group")
    public void listen(CardMessageDTO message) {
        try {
            Card card = cardService.create(message);
            System.out.println("Card created: " + card.getCardId());
        } catch (AccountInactiveException e){
            System.out.println("Skipping card creation. Card status is:  " + e.getMessage());
        }
    }
}
