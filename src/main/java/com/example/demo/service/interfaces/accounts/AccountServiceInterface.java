package com.example.demo.service.interfaces.accounts;

import com.example.demo.model.accounts.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountServiceInterface {

    // GET A LIST OF ALL EXISTING ACCOUNTS
    List<Account> getAllAccounts();

    // GET AN ACCOUNT'S DETAILS BY ACCOUNT NUMBER
    public Account getAccountByNumber(Long accountNumber);

    // MODIFY AN ACCOUNTS BALANCE BY ACCOUNT NUMBER
    void updateAccountBalance(Long accountNumber, BigDecimal newBalanceAmount);

    // DELETE AN ACCOUNT BY ACCOUNT NUMBER
    void deleteAccountByNumber(Long accountNumber);

}
