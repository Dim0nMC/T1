package org.example.clientprocessing.service;

import org.example.clientprocessing.exception.BlacklistException;
import org.example.clientprocessing.exception.DuplicateUserException;
import org.example.clientprocessing.mapper.ClientUserMapper;
import org.example.clientprocessing.model.Client;
import org.example.clientprocessing.model.User;
import org.example.clientprocessing.model.dto.ClientDTO;
import org.example.clientprocessing.model.dto.ClientRegistrationDTO;
import org.example.clientprocessing.model.dto.UserDTO;
import org.example.clientprocessing.repository.BlacklistRegistryRepository;
import org.example.clientprocessing.repository.ClientRepository;
import org.example.clientprocessing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final BlacklistRegistryRepository blacklistRegistryRepository;
    private final ClientIdGeneratorService clientIdGeneratorService;
    private final ClientUserMapper mapper;

    @Autowired
    public ClientService(ClientRepository clientRepository,
                         UserRepository userRepository,
                         UserService userService,
                         BlacklistRegistryRepository blacklistRegistryRepository,
                         ClientIdGeneratorService clientIdGeneratorService,
                         ClientUserMapper mapper) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.blacklistRegistryRepository = blacklistRegistryRepository;
        this.clientIdGeneratorService = clientIdGeneratorService;
        this.mapper = mapper;
    }

    public Client create(ClientDTO clientDTO, User user) {
        Client client = new Client();
        //client.setClientId(clientDTO.getClientId());
        client.setUser(user);
        client.setFirstName(clientDTO.getFirstName());
        client.setMiddleName(clientDTO.getMiddleName());
        client.setLastName(clientDTO.getLastName());
        client.setDateOfBirth(clientDTO.getDateOfBirth());
        client.setDocumentType(clientDTO.getDocumentType());
        client.setDocumentId(clientDTO.getDocumentId());
        client.setDocumentPrefix(clientDTO.getDocumentPrefix());
        client.setDocumentSuffix(clientDTO.getDocumentSuffix());
        clientRepository.save(client);
        return client;
    }


    public User register(ClientRegistrationDTO clientRegistrationDTO) {

        boolean isBlacklisted = blacklistRegistryRepository.existsByDocument_TypeAndDocument_Id(
                clientRegistrationDTO.getDocumentType(), clientRegistrationDTO.getDocumentId()
        );

        if (isBlacklisted) {
            throw new BlacklistException("Client is blacklisted");
        }

        if (userRepository.existsByLogin(clientRegistrationDTO.getLogin())) {
            throw new DuplicateUserException("User with this login already exists");
        }

        ClientDTO clientDTO = mapper.toClientDTO(clientRegistrationDTO);
        //clientDTO.setClientId(clientIdGeneratorService.generateClientId(clientRegistrationDTO));
        UserDTO userDTO = mapper.toUserDTO(clientRegistrationDTO);

        User user = userService.create(userDTO);
        Client client = this.create(clientDTO, user);

        String generatedClientId = clientIdGeneratorService.generateClientId(client.getId());
        client.setClientId(generatedClientId);
        clientRepository.save(client);

        return user;
    }
}
