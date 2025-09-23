package org.example.clientprocessing.service;


import org.example.clientprocessing.model.dto.ClientRegistrationDTO;
import org.springframework.stereotype.Service;

@Service
public class ClientIdGeneratorService {

    public String generateClientId(ClientRegistrationDTO dto) {
        //пока заглушка
        return "770100000001";
    }
}
