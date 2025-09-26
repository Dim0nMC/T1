package org.example.clientprocessing.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.clientprocessing.mapper.ProductMapper;
import org.example.clientprocessing.model.Product;
import org.example.clientprocessing.model.dto.ProductRequestDTO;
import org.example.clientprocessing.model.dto.ProductResponseDTO;
import org.example.clientprocessing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = productMapper.toEntity(dto);
        return productMapper.toResponseDTO(productRepository.save(product));
    }

    public ProductResponseDTO getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return productMapper.toResponseDTO(product);
    }

    public List<ProductResponseDTO> getAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    public ProductResponseDTO update (Long id, ProductRequestDTO dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        product.setName(dto.getName());
        product.setKey(dto.getKey());
        product.setCreateDate(dto.getCreateDate());
        product.setProductId(dto.getProductId());
        return productMapper.toResponseDTO(productRepository.save(product));
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found");
        }

        productRepository.deleteById(id);
    }


}
