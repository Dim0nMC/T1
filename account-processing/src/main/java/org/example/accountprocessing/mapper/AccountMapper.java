package org.example.accountprocessing.mapper;

import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.dto.ClientProductMessageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface  AccountMapper {

    Account toEntity(ClientProductMessageDTO dto);


}
