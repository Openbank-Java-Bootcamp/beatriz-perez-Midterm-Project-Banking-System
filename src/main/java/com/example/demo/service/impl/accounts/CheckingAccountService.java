package com.example.demo.service.impl.accounts;

import com.example.demo.repository.accounts.CheckingAccountRepository;
import com.example.demo.service.interfaces.accounts.CheckingAccountServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckingAccountService implements CheckingAccountServiceInterface {

    @Autowired
    private CheckingAccountRepository CheckingAccountRepo;

    // Methods


}
