package org.example.accountprocessing.service;

import org.example.accountprocessing.mapper.AccountMapper;
import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.dto.ClientProductMessageDTO;
import org.example.accountprocessing.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public Account createAccount(ClientProductMessageDTO message) {
        Account account = accountMapper.toEntity(message);
        return accountRepository.save(account);
    }
}
