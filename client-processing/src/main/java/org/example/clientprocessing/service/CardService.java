package org.example.clientprocessing.service;

import org.example.clientprocessing.model.dto.CardMessageDTO;
import org.example.clientprocessing.model.dto.CardRequestDTO;
import org.example.clientprocessing.repository.ClientProductRepository;
import org.example.clientprocessing.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CardService {

    private final KafkaTemplate<String, CardMessageDTO> kafkaTemplate;
    private final ClientProductRepository clientProductRepository;


    @Autowired
    public CardService(KafkaTemplate<String, CardMessageDTO> kafkaTemplate,
                       ClientProductRepository clientProductRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.clientProductRepository = clientProductRepository;
    }

    public void createCard(CardRequestDTO dto) {

        clientProductRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        String cardId = UUID.randomUUID().toString();

        CardMessageDTO message = new CardMessageDTO(
                dto.getAccountId(),
                cardId,
                dto.getPaymentSystem());

        kafkaTemplate.send("client_cards", message);
    }
}

