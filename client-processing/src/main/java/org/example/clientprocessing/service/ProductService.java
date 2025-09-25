package org.example.clientprocessing.service;

import org.example.clientprocessing.mapper.ProductMapper;
import org.example.clientprocessing.model.Product;
import org.example.clientprocessing.model.dto.ProductRequestDTO;
import org.example.clientprocessing.model.dto.ProductResponseDTO;
import org.example.clientprocessing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Product create(ProductRequestDTO dto) {
        Product product = productMapper.toEntity(dto);
        return productRepository.save(product);
    }

    public Product getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return product;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }


    public Product update (Long id, ProductRequestDTO dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(dto.getName());
        product.setKey(dto.getKey());
        product.setCreateDate(dto.getCreateDate());
        product.setProductId(dto.getProductId());
        return productRepository.save(product);

    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }


}
