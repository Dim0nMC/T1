package org.example.clientprocessing.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.clientprocessing.kafka.ClientProductProducer;
import org.example.clientprocessing.mapper.ClientProductMapper;
import org.example.clientprocessing.model.*;
import org.example.clientprocessing.model.dto.*;
import org.example.clientprocessing.model.enums.Key;
import org.example.clientprocessing.model.enums.Status;
import org.example.clientprocessing.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientProductService {

    private final ClientProductRepository clientProductRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final ClientProductMapper clientProductMapper;
    private final ClientProductProducer producer;

    @Autowired
    public ClientProductService(ClientProductRepository clientProductRepository,
                                ClientRepository clientRepository,
                                ProductRepository productRepository,
                                ClientProductMapper clientProductMapper,
                                ClientProductProducer producer) {

        this.clientProductRepository = clientProductRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.clientProductMapper = clientProductMapper;
        this.producer = producer;
    }

    public ClientProductResponseDTO addProductToClient(ClientProductRequestDTO dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + dto.getClientId()));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + dto.getProductId()));

        ClientProduct clientProduct = new ClientProduct();
        clientProduct.setClient(client);
        clientProduct.setProduct(product);
        clientProduct.setStatus(dto.getStatus());
        clientProduct.setOpenDate(LocalDate.now());

        ClientProduct saved = clientProductRepository.save(clientProduct);

        if (isCreditProduct(product.getKey())) {
            producer.sendToClientCreditProducts("Client " + client.getId() + " opened product " + product.getProductId());
        } else {
            producer.sendToClientProducts("Client " + client.getId() + " opened product " + product.getProductId());
        }

        return clientProductMapper.toResponseDTO(saved);
    }

    public List<ClientProductResponseDTO> getAll() {
        return clientProductRepository.findAll().stream()
                .map(clientProductMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ClientProductResponseDTO getById (Long id) {
        ClientProduct clientProduct = clientProductRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ClientProduct not found with id: " + id));
        return clientProductMapper.toResponseDTO(clientProduct);
    }

    public ClientProductResponseDTO update(Long id, ClientProductRequestDTO dto){

        ClientProduct clientProduct = clientProductRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ClientProduct not found with id: " + id));

        if (dto.getStatus() != null)
        {
            clientProduct.setStatus(dto.getStatus());
        }

        if (dto.getCloseDate() != null) {
            clientProduct.setCloseDate(dto.getCloseDate());
        }

        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found: " + dto.getProductId()));
            clientProduct.setProduct(product);
        }

        return clientProductMapper.toResponseDTO(clientProductRepository.save(clientProduct));
    }

    public void closeClientProduct(Long id) {
        ClientProduct clientProduct = clientProductRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client product not found with id: "+ id));
        clientProduct.setCloseDate(LocalDate.now());
        clientProduct.setStatus(Status.CLOSED);
        clientProductRepository.save(clientProduct);
    }

    public void delete (Long id) {


        if (!clientProductRepository.existsById(id)) {
            throw new EntityNotFoundException("ClientProduct not found with id: " + id);
        }
        clientProductRepository.deleteById(id);
    }

    private boolean isCreditProduct(Key key) {
        return List.of("IPO", "PC", "AC").contains(key);
    }
}
