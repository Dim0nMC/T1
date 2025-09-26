package org.example.clientprocessing.mapper;

import org.example.clientprocessing.model.Client;
import org.example.clientprocessing.model.dto.ClientDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO toClientDTO(Client client);
}
