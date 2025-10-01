package org.example.accountprocessing.mapper;

import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Payment;
import org.example.accountprocessing.model.dto.ClientPaymentMessageDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping( ignore = true, target = "account" )
    Payment toEntity (ClientPaymentMessageDTO dto);

    @AfterMapping
    default void setRelations(ClientPaymentMessageDTO dto, @MappingTarget Payment payment) {
        if (dto.getAccountId() != null) {
            Account account = new Account();
            account.setId(dto.getAccountId());
            payment.setAccount(account);
        }
    }
}
