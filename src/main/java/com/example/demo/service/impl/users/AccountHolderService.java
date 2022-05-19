package com.example.demo.service.impl.users;

import com.example.demo.model.users.AccountHolder;
import com.example.demo.repository.users.AccountHolderRepository;
import com.example.demo.service.interfaces.security.RoleServiceInterface;
import com.example.demo.service.interfaces.users.AccountHolderServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountHolderService implements AccountHolderServiceInterface {

    @Autowired
    private AccountHolderRepository AccountHolderRepo;
    @Autowired
    private RoleServiceInterface roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Methods
    public AccountHolder createAccountHolder(AccountHolder accountHolder) {

        // Handle possible errors:
        if(AccountHolderRepo.findByUsername(accountHolder.getUsername()) != null) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "Element already exists" ); }
        // Encrypt secret key:
        accountHolder.setPassword(passwordEncoder.encode(accountHolder.getPassword()));
        // Save new user:
        log.info("Saving a new accountHolder {} in the DB", accountHolder.getUsername());
        AccountHolder dbHolder = AccountHolderRepo.save(accountHolder);
        // Add ACCOUNTHOLDER role to user (all AccountHolder class instances are ACCOUNTHOLDER users):
        roleService.addRoleToUser(accountHolder.getUsername(), "ROLE_ACCOUNTHOLDER");
        return dbHolder;
    }
}
