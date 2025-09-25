package org.example.clientprocessing.service;

import lombok.RequiredArgsConstructor;
import org.example.clientprocessing.kafka.ClientProductProducer;
import org.example.clientprocessing.mapper.ClientProductMapper;
import org.example.clientprocessing.model.*;
import org.example.clientprocessing.model.dto.*;
import org.example.clientprocessing.model.enums.Key;
import org.example.clientprocessing.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientProductService {

    private final ClientProductRepository clientProductRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final ClientProductMapper mapper;
    private final ClientProductProducer producer;

    public ClientProductResponseDTO addProductToClient(ClientProductRequestDTO dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        ClientProduct clientProduct = ClientProduct.builder()
                .client(client)
                .product(product)
                .status(dto.getStatus())
                .openDate(LocalDate.now())
                .build();

        ClientProduct saved = clientProductRepository.save(clientProduct);

        // Отправляем сообщение в Kafka
        if (isCreditProduct(product.getKey())) {
            producer.sendToClientCreditProducts("Client " + client.getId() + " opened product " + product.getKey());
        } else {
            producer.sendToClientProducts("Client " + client.getId() + " opened product " + product.getKey());
        }

        return mapper.toResponseDTO(saved);
    }

    public List<ClientProductResponseDTO> get(Long clientId) {
        return clientProductRepository.findAll().stream()
                .filter(cp -> cp.getClient().getId().equals(clientId))
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void closeClientProduct(Long id) {
        ClientProduct clientProduct = clientProductRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client product not found"));
        clientProduct.setCloseDate(LocalDate.now());
        clientProduct.setStatus("CLOSED");
        clientProductRepository.save(clientProduct);
    }

    private boolean isCreditProduct(Key key) {
        return List.of("IPO", "PC", "AC").contains(key);
    }
}
