package com.example.demo.service.impl.accounts;

import com.example.demo.model.accounts.SavingsAccount;
import com.example.demo.model.aux.Money;
import com.example.demo.repository.accounts.SavingsAccountRepository;
import com.example.demo.service.interfaces.accounts.SavingsAccountServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavingsAccountService implements SavingsAccountServiceInterface {

    @Autowired
    private SavingsAccountRepository SavingsAccountRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Methods
    public SavingsAccount createSavingsAccount(SavingsAccount account) {
        // Handle possible errors:
        // Set MINIMUM BALANCE according to allowed range:
        BigDecimal maxMinimumBalance = new BigDecimal("1000");
        BigDecimal minMinimumBalance = new BigDecimal("100");
        if( account.getMinimumBalance().getAmount().compareTo(maxMinimumBalance) == 1 ) {
            account.setMinimumBalance(new Money(maxMinimumBalance));
            log.error("Savings accounts should have a minimum balance of 1000 as a maximum. Minimum balance has been set to 1000.");
        }
        if( account.getMinimumBalance().getAmount().compareTo(minMinimumBalance) == -1 ) {
            account.setMinimumBalance(new Money(minMinimumBalance));
            log.error("Savings accounts should have a minimum balance of 100 as a minimum. Minimum balance has been set to 100.");
        }
        // Encrypt secret key:
        account.setSecretKey(passwordEncoder.encode(account.getSecretKey()));
        // Save new account:
        log.info("Saving a new Savings Account in the DB");
        return SavingsAccountRepo.save(account);
    }



}
