package org.example.clientprocessing.service;


import org.example.clientprocessing.model.dto.ClientRegistrationDTO;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ClientIdGeneratorService {

    public String generateClientId(Long clientId) {

        Random random = new Random();

        int region = random.nextInt(90) + 10; // XX
        int branch = random.nextInt(90) + 10; // FF
        String number = String.format("%08d", clientId); // NNNNNNNN = id

        return String.format("%02d%02d%s", region, branch, number);
    }
}
