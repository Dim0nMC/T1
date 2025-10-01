package org.example.accountprocessing.service;

import org.example.accountprocessing.exception.AccountInactiveException;
import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Card;
import org.example.accountprocessing.model.dto.CardMessageDTO;
import org.example.accountprocessing.model.enums.AccountStatus;
import org.example.accountprocessing.repository.AccountRepository;
import org.example.accountprocessing.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public CardService(CardRepository cardRepository, AccountRepository accountRepository) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    public Card create(CardMessageDTO message) {
        Account account = accountRepository.getById(message.getAccountId());

        if(account.getStatus() == AccountStatus.ACTIVE) {
            Card card = new Card();
            card.setAccount(account);
            card.setCardId(message.getCardId());
            card.setPaymentSystem(message.getPaymentSystem());
            cardRepository.save(card);

            account.setCardExist(true);
            accountRepository.save(account);

            return card;
        }
        else throw new AccountInactiveException(account.getStatus().toString());
    }
}
