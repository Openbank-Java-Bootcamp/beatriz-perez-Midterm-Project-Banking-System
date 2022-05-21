package com.example.demo.service.impl.accounts;

import com.example.demo.DTO.NewAccountDTO;
import com.example.demo.model.accounts.SavingsAccount;
import com.example.demo.model.secondary.Money;
import com.example.demo.repository.accounts.SavingsAccountRepository;
import com.example.demo.repository.users.AccountHolderRepository;
import com.example.demo.service.interfaces.accounts.SavingsAccountServiceInterface;
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
public class SavingsAccountService implements SavingsAccountServiceInterface {

    @Autowired
    private SavingsAccountRepository SavingsAccountRepo;
    @Autowired
    private AccountHolderRepository accHolderRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Methods

    // CREATE A NEW SAVINGS ACCOUNT
    public SavingsAccount createSavingsAccount(NewAccountDTO accountDTO) {
        SavingsAccount account = new SavingsAccount(
                accountDTO.getSecretKey(),
                accHolderRepo.findById( accountDTO.getPrimaryOwnerId() ).get(),
                null,
                accountDTO.getBalanceAmount(),
                Currency.getInstance( accountDTO.getCurrencyCode() )
        );
        // Handle possible errors:
            // balanceAmount < min -------------------------------------------
        // Set MINIMUM BALANCE according to allowed range:
        checkMinimumBalance(account);
        // Encrypt secret key:
        account.setSecretKey(passwordEncoder.encode(account.getSecretKey()));
        // Save new account:
        log.info("Saving a new Savings Account in the DB");
        return SavingsAccountRepo.save(account);
    }

    public SavingsAccount createSavingsAccount(SavingsAccount account) {
        // Handle possible errors:
            // balanceAmount < min -------------------------------------------
        // Set MINIMUM BALANCE according to allowed range:
        checkMinimumBalance(account);
        // Encrypt secret key:
        account.setSecretKey(passwordEncoder.encode(account.getSecretKey()));
        // Save new account:
        log.info("Saving a new Savings Account in the DB");
        return SavingsAccountRepo.save(account);
    }


    // CHECK MINIMUM BALANCE
    public void checkMinimumBalance(SavingsAccount account) {
        BigDecimal maxMinimumBalanceAmount = new BigDecimal("1000");
        BigDecimal minMinimumBalanceAmount = new BigDecimal("100");
        if( account.getMinimumBalance().getAmount().compareTo(maxMinimumBalanceAmount) == 1 ) {
            account.setMinimumBalance(new Money(maxMinimumBalanceAmount));
            log.error("Savings accounts should have a minimum balance of {} as a maximum. Minimum balance has been set to this maximum value.", maxMinimumBalanceAmount);
        }
        if( account.getMinimumBalance().getAmount().compareTo(minMinimumBalanceAmount) == -1 ) {
            account.setMinimumBalance(new Money(minMinimumBalanceAmount));
            log.error("Savings accounts should have a minimum balance of {} as a minimum. Minimum balance has been set to this minimum value.", minMinimumBalanceAmount);
        }
    }
}
