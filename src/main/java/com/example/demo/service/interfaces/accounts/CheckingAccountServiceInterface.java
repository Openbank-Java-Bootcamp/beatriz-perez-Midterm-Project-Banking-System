package com.example.demo.service.interfaces.accounts;

import com.example.demo.DTO.NewAccountDTO;
import com.example.demo.model.accounts.CheckingAccount;

public interface CheckingAccountServiceInterface {

    // CREATE A NEW CHECKING ACCOUNT
    CheckingAccount createCheckingAccount(NewAccountDTO accountDTO);
    CheckingAccount createCheckingAccount(CheckingAccount account);

    // CHECK ACCOUNT OWNER'S AGE
    void checkAge(CheckingAccount account);

}
