package com.example.demo.controller.interfaces.accounts;

import com.example.demo.model.accounts.Account;
import com.example.demo.model.accounts.CheckingAccount;
import com.example.demo.model.accounts.CreditCardAccount;
import com.example.demo.model.accounts.SavingsAccount;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AccountControllerInterface {

    // Get a list of all active accounts
    List<Account> getAllAccounts();
    // Get account details by accountNumber
    Account getAccountByNumber(String number);

    // Create a new Checking Account
    void createCheckingAccount(CheckingAccount checkingAccount);
    // Create a new Credit Card Account
    void createCreditCardAccount(CreditCardAccount creditCardAccount);
    // Create a new Savings Account
    void createSavingsAccount(SavingsAccount savingsAccount);



    // Delete account by accountNumber
    void deleteAccountByNumber(String number);

}
