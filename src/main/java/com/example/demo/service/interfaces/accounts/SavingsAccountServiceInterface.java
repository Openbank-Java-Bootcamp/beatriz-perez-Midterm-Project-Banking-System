package com.example.demo.service.interfaces.accounts;

import com.example.demo.DTO.NewSavingsAccountDTO;
import com.example.demo.model.accounts.SavingsAccount;

public interface SavingsAccountServiceInterface {

    // CREATE A NEW SAVINGS ACCOUNT
    SavingsAccount createSavingsAccount(NewSavingsAccountDTO accountDTO);
    SavingsAccount createSavingsAccount(SavingsAccount account);

    // CHECK MINIMUM BALANCE
    void checkMinimumBalance(SavingsAccount account);

}
