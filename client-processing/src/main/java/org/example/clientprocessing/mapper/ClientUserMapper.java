package org.example.clientprocessing.mapper;


import org.example.clientprocessing.model.dto.ClientDTO;
import org.example.clientprocessing.model.dto.ClientRegistrationDTO;
import org.example.clientprocessing.model.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientUserMapper {


    ClientDTO toClientDTO(ClientRegistrationDTO dto);

    UserDTO toUserDTO(ClientRegistrationDTO dto);

    ClientRegistrationDTO toClientRegistrationDTO(ClientDTO clientDTO, UserDTO userDTO);

}
