package com.example.demo.service.impl.users;

import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.users.User;
import com.example.demo.repository.users.AccountHolderRepository;
import com.example.demo.service.interfaces.users.AccountHolderServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountHolderService implements AccountHolderServiceInterface {

    @Autowired
    private AccountHolderRepository AccountHolderRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Methods
    public AccountHolder saveAccountHolder(AccountHolder accountHolder) {

        // Handle possible errors:
        if(AccountHolderRepo.findByUsername(accountHolder.getUsername()) != null) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "Element already exists" ); }
        // Save new user:
        log.info("Saving a new accountHolder {} in the DB", accountHolder.getUsername());
        accountHolder.setPassword(passwordEncoder.encode(accountHolder.getPassword()));
        return AccountHolderRepo.save(accountHolder);
    }
}
