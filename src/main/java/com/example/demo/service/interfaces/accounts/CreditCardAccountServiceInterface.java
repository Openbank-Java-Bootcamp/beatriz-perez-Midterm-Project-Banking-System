package com.example.demo.service.interfaces.accounts;

import com.example.demo.DTO.NewCreditCardAccountDTO;
import com.example.demo.model.accounts.CreditCardAccount;

public interface CreditCardAccountServiceInterface {

    // CREATE A NEW CREDIT CARD ACCOUNT
    CreditCardAccount createCreditCardAccount(NewCreditCardAccountDTO accountDTO);
    CreditCardAccount createCreditCardAccount(CreditCardAccount account);

    // CHECK CREDIT LIMIT
    void checkCreditLimit(CreditCardAccount account);

}
