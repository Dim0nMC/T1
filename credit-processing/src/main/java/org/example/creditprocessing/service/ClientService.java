package org.example.creditprocessing.service;

import org.example.creditprocessing.model.dto.ClientInfoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ClientService {

    private final WebClient webClient;

    public ClientService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://ms1:8080/clients").build();
    }

    public ClientInfoDTO getClientInfo(Long clientId) {
        return webClient.get()
                .uri("/{id}", clientId)
                .retrieve()
                .bodyToMono(ClientInfoDTO.class)
                .block();
    }
}
