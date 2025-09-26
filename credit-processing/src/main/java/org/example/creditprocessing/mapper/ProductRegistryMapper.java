package org.example.creditprocessing.mapper;

import org.example.creditprocessing.config.CreditConfig;
import org.example.creditprocessing.model.ProductRegistry;
import org.example.creditprocessing.model.dto.ClientProductCreditMessageDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public abstract class ProductRegistryMapper {

    @Value("${credit.interest-rate}")
    private BigDecimal interestRate;

    public abstract ProductRegistry toEntity (ClientProductCreditMessageDTO dto);

    @AfterMapping
    protected void setInterestRate(@MappingTarget ProductRegistry entity) {
        entity.setInterestRate(interestRate);
    }
}
