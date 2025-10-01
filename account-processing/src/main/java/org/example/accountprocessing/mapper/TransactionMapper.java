package org.example.accountprocessing.mapper;

import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Card;
import org.example.accountprocessing.model.Transaction;
import org.example.accountprocessing.model.dto.ClientTransactionMessageDTO;
import org.example.accountprocessing.model.dto.TransactionDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDTO toTransactionDTO(ClientTransactionMessageDTO message);

    @Mappings({
            @Mapping(ignore = true, target = "card"),
            @Mapping(ignore = true, target = "account")
    })
    Transaction toEntity(TransactionDTO dto);

    @AfterMapping
    default void setRelations(TransactionDTO dto, @MappingTarget Transaction transaction) {
        if (dto.getCardId() != null) {
            Card card = new Card();
            card.setId(dto.getCardId());
            transaction.setCard(card);
        }
        if (dto.getAccountId() != null) {
            Account account = new Account();
            account.setId(dto.getAccountId());
            transaction.setAccount(account);
        }
        }
}
