package com.example.demo.service.impl.users;

import com.example.demo.repository.users.AccountHolderRepository;
import com.example.demo.service.interfaces.users.AccountHolderServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountHolderService implements AccountHolderServiceInterface {

    @Autowired
    private AccountHolderRepository AccountHolderRepo;

    // Methods

}
