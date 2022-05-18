package com.example.demo.service.impl.accounts;

import com.example.demo.enums.AccountType;
import com.example.demo.model.accounts.CheckingAccount;
import com.example.demo.model.aux.Money;
import com.example.demo.model.users.AccountHolder;
import com.example.demo.repository.accounts.CheckingAccountRepository;
import com.example.demo.service.interfaces.accounts.CheckingAccountServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckingAccountService implements CheckingAccountServiceInterface {

    @Autowired
    private CheckingAccountRepository CheckingAccountRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Methods
    public CheckingAccount createCheckingAccount(CheckingAccount account) {
        // Handle possible errors:

        // Set account type and conditions according to owner's age:
        Integer age = checkAge(account.getPrimaryOwner());
        if( age > 24 ) {
            account.setType(AccountType.REGULAR);
            account.setMinimumBalance(new Money(new BigDecimal("250"), account.getBalance().getCurrency()));
            account.setMonthlyMaintenanceFee(new Money(new BigDecimal("12"), account.getBalance().getCurrency()));
        }

        // Encrypt secret key:
        account.setSecretKey(passwordEncoder.encode(account.getSecretKey()));
        // Save new account:
        log.info("Saving a new Checking Account in the DB");
        return CheckingAccountRepo.save(account);
    }

    public Integer checkAge(AccountHolder primaryOwner) {
        Date today = new Date();
        Date birthDate = primaryOwner.getDateOfBirth();

        Integer age = today.getYear() - birthDate.getYear();
        if (today.getMonth() < birthDate.getMonth()) age--;
        if (today.getMonth() == birthDate.getMonth() && today.getDay() < birthDate.getDay()) age--;
        return age;
    }
}
