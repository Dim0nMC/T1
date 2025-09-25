package org.example.clientprocessing.mapper;

import org.example.clientprocessing.model.ClientProduct;
import org.example.clientprocessing.model.dto.ClientProductResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientProductMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "product.id", target = "productId")
    ClientProductResponseDTO toResponseDTO(ClientProduct entity);

}
