package com.example.demo.controller.interfaces.accounts;

import com.example.demo.DTO.*;
import com.example.demo.model.accounts.Account;
import org.springframework.web.bind.annotation.RequestBody;

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
    // Operate as Third Party
    void operateAsThirdParty(ThirdPartyTransactionDTO transactionDto);

    // Delete account by accountNumber
    void deleteAccountByNumber(String number);

}
