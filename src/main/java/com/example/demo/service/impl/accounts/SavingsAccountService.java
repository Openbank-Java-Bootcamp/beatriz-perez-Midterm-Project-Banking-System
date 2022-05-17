package com.example.demo.service.impl.accounts;

import com.example.demo.repository.accounts.SavingsAccountRepository;
import com.example.demo.service.interfaces.accounts.SavingsAccountServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavingsAccountService implements SavingsAccountServiceInterface {

    @Autowired
    private SavingsAccountRepository SavingsAccountRepo;

    // Methods


}
