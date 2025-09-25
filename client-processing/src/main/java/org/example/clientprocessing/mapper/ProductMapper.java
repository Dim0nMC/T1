package org.example.clientprocessing.mapper;

import org.example.clientprocessing.model.Product;
import org.example.clientprocessing.model.dto.ProductRequestDTO;
import org.example.clientprocessing.model.dto.ProductResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDTO dto);

    ProductResponseDTO toResponseDTO(Product product);
}
