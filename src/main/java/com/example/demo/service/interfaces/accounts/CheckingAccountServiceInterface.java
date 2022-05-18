package com.example.demo.service.interfaces.accounts;

import com.example.demo.model.accounts.CheckingAccount;
import com.example.demo.model.users.AccountHolder;

public interface CheckingAccountServiceInterface {

    CheckingAccount createCheckingAccount(CheckingAccount account);

    Integer checkAge(AccountHolder primaryOwner);

}
