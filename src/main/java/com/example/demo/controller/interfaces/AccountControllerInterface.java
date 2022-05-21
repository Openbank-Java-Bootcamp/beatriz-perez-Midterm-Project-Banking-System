package com.example.demo.controller.interfaces;

import com.example.demo.DTO.*;
import com.example.demo.model.accounts.Account;

import java.util.List;

public interface AccountControllerInterface {

    // Get a list of all active accounts
    List<Account> getAllAccounts();
    // Get a list of all active accounts by owner ID
    List<Account> getAllAccountsByOwner( String id );
    // Get account details by accountNumber
    Account getAccountByNumber(String number);

    // Get a list of all MY active accounts (primary AND secondary owner) as authenticated user
    List<Account> getAllMyAccounts();
    // Get account details of one of MY accounts by accountNumber (primary AND secondary owner) as authenticated user


    // Create a new Checking Account
    void createCheckingAccount(NewAccountDTO accountDTO);
    // Create a new Credit Card Account
    void createCreditCardAccount(NewAccountDTO accountDTO);
    // Create a new Savings Account
    void createSavingsAccount(NewAccountDTO accountDTO);

    // Modify an account's balance
    void updateAccountBalance(String number, AccountBalanceOnlyDTO accountDto);
    // Operate as Third Party
    void operateAsThirdParty(ThirdPartyTransactionDTO transactionDto);
    // Transfer money as an Account Holder
    void transferMoney(TransferDTO transferDto);

    // Delete account by accountNumber
    void deleteAccountByNumber(String number);

}
