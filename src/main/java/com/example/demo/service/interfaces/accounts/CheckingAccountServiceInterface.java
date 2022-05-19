package com.example.demo.service.interfaces.accounts;

import com.example.demo.model.accounts.CheckingAccount;
import com.example.demo.model.users.AccountHolder;

public interface CheckingAccountServiceInterface {

    // CREATE A NEW CHECKING ACCOUNT
    CheckingAccount createCheckingAccount(CheckingAccount account);

    // CHECK ACCOUNT OWNER'S AGE
    void checkAge(CheckingAccount account);

}
