package org.example.clientprocessing.mapper;

import org.example.clientprocessing.model.Client;
import org.example.clientprocessing.model.ClientProduct;
import org.example.clientprocessing.model.Product;
import org.example.clientprocessing.model.dto.ClientProductResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClientProductMapper {

    @Mappings({
            @Mapping(source = "client.id", target = "clientId"),
            @Mapping(source = "product.id", target = "productId")
    })
    ClientProductResponseDTO toResponseDTO(ClientProduct clientProduct);

    @Mappings({
            @Mapping(ignore = true, target = "client"),
            @Mapping(ignore = true, target = "product")
    })
    ClientProduct toEntity(ClientProductResponseDTO dto);

    @AfterMapping
    default void setRelations(ClientProductResponseDTO dto, @MappingTarget ClientProduct clientProduct) {
        if (dto.getClientId() != null) {
            Client client = new Client();
            client.setId(dto.getClientId());
            clientProduct.setClient(client);
        }
        if (dto.getProductId() != null) {
            Product product = new Product();
            product.setId(dto.getProductId());
            clientProduct.setProduct(product);
        }
    }




}
