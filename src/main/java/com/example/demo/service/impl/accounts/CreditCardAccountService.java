package com.example.demo.service.impl.accounts;

import com.example.demo.repository.accounts.CreditCardAccountRepository;
import com.example.demo.service.interfaces.accounts.CreditCardAccountServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditCardAccountService implements CreditCardAccountServiceInterface {

    @Autowired
    private CreditCardAccountRepository CreditCardAccountRepo;

    // Methods


}
