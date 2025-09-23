package org.example.clientprocessing.mapper;


import org.example.clientprocessing.model.dto.ClientDTO;
import org.example.clientprocessing.model.dto.ClientRegistrationDTO;
import org.example.clientprocessing.model.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientUserMapper {

    // Map ClientRegistrationDTO -> ClientDTO
    ClientDTO toClientDTO(ClientRegistrationDTO dto);

    // Map ClientRegistrationDTO -> UserDTO
    UserDTO toUserDTO(ClientRegistrationDTO dto);

    // Map обратно (по необходимости)
    ClientRegistrationDTO fromClientDTO(ClientDTO clientDTO, UserDTO userDTO);

}
