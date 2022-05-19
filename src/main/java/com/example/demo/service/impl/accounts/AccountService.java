package com.example.demo.service.impl.accounts;

import com.example.demo.model.accounts.Account;
import com.example.demo.model.users.User;
import com.example.demo.repository.accounts.AccountRepository;
import com.example.demo.service.interfaces.accounts.AccountServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements AccountServiceInterface {

    @Autowired
    private AccountRepository AccountRepo;

    // Methods

    // GET A LIST OF ALL EXISTING ACCOUNTS
    public List<Account> getAllAccounts() {
        // Handle possible errors:
        if(AccountRepo.findAll().size() == 0) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "No elements to show" ); }
        // Return results
        log.info("Fetching all accounts");
        return AccountRepo.findAll();
    }

    // GET AN ACCOUNT'S DETAILS BY ACCOUNT NUMBER
    public Account getAccountByNumber(Long accountNumber) {
        // Handle possible errors:
        if(AccountRepo.findById(accountNumber).isEmpty()){ throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account found with the specified number"); }
        // Return user:
        log.info("Fetching account information");
        return AccountRepo.findById(accountNumber).get();
    }

    // DELETE AN ACCOUNT BY ACCOUNT NUMBER
    public void deleteAccountByNumber(Long accountNumber) {
        Optional<Account> account = AccountRepo.findById(accountNumber);
        // Handle possible errors:
        if(account.isEmpty()){ throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account found with the specified number"); }
        // Delete account:
        log.info("Deleting account");
        AccountRepo.delete(account.get());
    }

}
