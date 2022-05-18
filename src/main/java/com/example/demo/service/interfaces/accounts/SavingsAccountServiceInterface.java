package com.example.demo.service.interfaces.accounts;

import com.example.demo.model.accounts.SavingsAccount;

public interface SavingsAccountServiceInterface {

    SavingsAccount createSavingsAccount(SavingsAccount account);

    void checkMinimumBalance(SavingsAccount account);

}
