package com.example.demo.controller.interfaces.accounts;

import com.example.demo.DTO.AccountBalanceOnlyDTO;
import com.example.demo.DTO.NewCheckingAccountDTO;
import com.example.demo.DTO.NewCreditCardAccountDTO;
import com.example.demo.DTO.NewSavingsAccountDTO;
import com.example.demo.model.accounts.Account;

import java.util.List;

public interface AccountControllerInterface {

    // Get a list of all active accounts
    List<Account> getAllAccounts();
    // Get account details by accountNumber
    Account getAccountByNumber(String number);

    // Create a new Checking Account
    void createCheckingAccount(NewCheckingAccountDTO accountDTO);
    // Create a new Credit Card Account
    void createCreditCardAccount(NewCreditCardAccountDTO accountDTO);
    // Create a new Savings Account
    void createSavingsAccount(NewSavingsAccountDTO accountDTO);

    // Modify an account's balance
    void updateAccountBalance(String number, AccountBalanceOnlyDTO accountDto);

    // Delete account by accountNumber
    void deleteAccountByNumber(String number);

}
