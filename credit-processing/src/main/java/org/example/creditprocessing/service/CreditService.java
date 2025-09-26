package org.example.creditprocessing.service;


import org.example.creditprocessing.mapper.ProductRegistryMapper;
import org.example.creditprocessing.model.ProductRegistry;
import org.example.creditprocessing.model.dto.ClientInfoDTO;
import org.example.creditprocessing.model.dto.ClientProductCreditMessageDTO;
import org.example.creditprocessing.repository.ProductRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreditService {

    private final ClientService clientService;
    private final CreditDecisionService creditDecisionService;
    private final ProductRegistryMapper productRegistryMapper;
    private final ProductRegistryRepository productRegistryRepository;
    private final PaymentRegistryService paymentRegistryService;

    @Autowired
    public CreditService(ClientService clientService,
                         CreditDecisionService creditDecisionService,
                         ProductRegistryMapper productRegistryMapper,
                         ProductRegistryRepository productRegistryRepository,
                         PaymentRegistryService paymentRegistryService) {

        this.clientService = clientService;
        this.creditDecisionService = creditDecisionService;
        this.productRegistryMapper = productRegistryMapper;
        this.productRegistryRepository = productRegistryRepository;
        this.paymentRegistryService = paymentRegistryService;
    }

    public void addProduct(ClientProductCreditMessageDTO message) {

        ClientInfoDTO clientInfoDTO = clientService.getClientInfo(message.getClientId());

        if (creditDecisionService.approveCredit(message.getClientId(), message.getAmount())){
            ProductRegistry productRegistry = productRegistryMapper.toEntity(message);
            productRegistryRepository.save(productRegistry);
            paymentRegistryService.generatePaymentSchedule(productRegistry, message.getAmount());
            System.out.println("Credit of " + message.getAmount()+ " was issued to: "+message.getAccountId());
        }
        else{
            //отказ
            System.out.println("Credit was refued to " + message.getAccountId());
        }
    }
}
