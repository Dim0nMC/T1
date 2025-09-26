package org.example.clientprocessing.controller;

import org.example.clientprocessing.model.dto.CardRequestDTO;
import org.example.clientprocessing.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<String> createCard(@RequestBody CardRequestDTO dto) {
        cardService.createCard(dto);
        return ResponseEntity.ok("Card request sent to processing");
    }
}

