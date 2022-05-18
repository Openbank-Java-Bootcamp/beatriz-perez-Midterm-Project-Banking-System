package com.example.demo.service.interfaces.accounts;

import com.example.demo.model.accounts.CreditCardAccount;

public interface CreditCardAccountServiceInterface {

    CreditCardAccount createCreditCardAccount(CreditCardAccount account);

    void checkCreditLimit(CreditCardAccount account);

}
