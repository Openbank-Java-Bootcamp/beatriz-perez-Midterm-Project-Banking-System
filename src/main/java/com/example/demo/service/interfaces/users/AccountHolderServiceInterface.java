package com.example.demo.service.interfaces.users;

import com.example.demo.model.users.AccountHolder;

public interface AccountHolderServiceInterface {

    // CREATE A NEW ACCOUNT HOLDER
    AccountHolder createAccountHolder(AccountHolder accountHolder);
    // UPDATE AN ACCOUNT HOLDER BY ID
    void updateAccountHolderById(Long id, AccountHolder accountHolder);

}
