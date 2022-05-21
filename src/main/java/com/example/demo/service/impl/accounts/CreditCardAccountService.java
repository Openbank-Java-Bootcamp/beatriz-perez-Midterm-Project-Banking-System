package com.example.demo.service.impl.accounts;

import com.example.demo.DTO.NewAccountDTO;
import com.example.demo.model.accounts.CreditCardAccount;
import com.example.demo.model.secondary.Money;
import com.example.demo.repository.accounts.CreditCardAccountRepository;
import com.example.demo.repository.users.AccountHolderRepository;
import com.example.demo.service.interfaces.accounts.CreditCardAccountServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditCardAccountService implements CreditCardAccountServiceInterface {

    @Autowired
    private CreditCardAccountRepository CreditCardAccountRepo;
    @Autowired
    private AccountHolderRepository accHolderRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Methods

    // CREATE A NEW CREDIT CARD ACCOUNT
    public CreditCardAccount createCreditCardAccount(NewAccountDTO accountDTO) {
        CreditCardAccount account = new CreditCardAccount(
                accountDTO.getSecretKey(),
                accHolderRepo.findById( accountDTO.getPrimaryOwnerId() ).get(),
                null,
                accountDTO.getBalanceAmount(),
                Currency.getInstance( accountDTO.getCurrencyCode() )
        );
        // Set CREDIT LIMIT according to allowed range:
        checkCreditLimit(account);
        // Encrypt secret key:
        account.setSecretKey(passwordEncoder.encode(account.getSecretKey()));
        // Save new account:
        log.info("Saving a new CreditCard Account in the DB");
        return CreditCardAccountRepo.save(account);
    }
    public CreditCardAccount createCreditCardAccount(CreditCardAccount account) {
        // Set CREDIT LIMIT according to allowed range:
        checkCreditLimit(account);
        // Encrypt secret key:
        account.setSecretKey(passwordEncoder.encode(account.getSecretKey()));
        // Save new account:
        log.info("Saving a new CreditCard Account in the DB");
        return CreditCardAccountRepo.save(account);
    }

    // CHECK CREDIT LIMIT
    public void checkCreditLimit(CreditCardAccount account) {
        BigDecimal maxCreditLimitAmount = new BigDecimal("100000");
        BigDecimal minCreditLimitAmount = new BigDecimal("100");
        if( account.getCreditLimit().getAmount().compareTo(maxCreditLimitAmount) == 1 ) {
            account.setCreditLimit(new Money(maxCreditLimitAmount));
            log.error("CreditCard accounts should have a maximum credit limit of {}. Credit limit has been set to this maximum value.", maxCreditLimitAmount);
        }
        if( account.getCreditLimit().getAmount().compareTo(minCreditLimitAmount) == -1 ) {
            account.setCreditLimit(new Money(minCreditLimitAmount));
            log.error("CreditCard accounts should have a minimum credit limit of {}. Credit limit has been set to this minimum value.", minCreditLimitAmount);
        }
    }
}
