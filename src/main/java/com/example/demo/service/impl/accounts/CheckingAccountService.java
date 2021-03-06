package com.example.demo.service.impl.accounts;

import com.example.demo.DTO.NewAccountDTO;
import com.example.demo.enums.AccountType;
import com.example.demo.model.accounts.CheckingAccount;
import com.example.demo.model.secondary.Money;
import com.example.demo.repository.accounts.CheckingAccountRepository;
import com.example.demo.repository.users.AccountHolderRepository;
import com.example.demo.service.interfaces.accounts.CheckingAccountServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Currency;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckingAccountService implements CheckingAccountServiceInterface {

    @Autowired
    private CheckingAccountRepository CheckingAccountRepo;
    @Autowired
    private AccountHolderRepository accHolderRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Methods

    // CREATE A NEW CHECKING ACCOUNT
    public CheckingAccount createCheckingAccount(NewAccountDTO accountDTO) {
        CheckingAccount account = new CheckingAccount(
                accountDTO.getSecretKey(),
                accHolderRepo.findById( accountDTO.getPrimaryOwnerId() ).get(),
                null,
                accountDTO.getBalanceAmount(),
                Currency.getInstance( accountDTO.getCurrencyCode() )
        );
        // Set account type and conditions according to owner's age:
        checkAge(account);
        // Encrypt secret key:
        account.setSecretKey(passwordEncoder.encode(account.getSecretKey()));
        // Save new account:
        log.info("Saving a new Checking Account in the DB");
        return CheckingAccountRepo.save(account);
    }
    public CheckingAccount createCheckingAccount(CheckingAccount account) {
        // Set account type and conditions according to owner's age:
        checkAge(account);
        // Encrypt secret key:
        account.setSecretKey(passwordEncoder.encode(account.getSecretKey()));
        // Save new account:
        log.info("Saving a new Checking Account in the DB");
        return CheckingAccountRepo.save(account);
    }


    // CHECK ACCOUNT OWNER'S AGE
    public void checkAge(CheckingAccount account) {
        LocalDate today = LocalDate.now();
        LocalDate birthDate = account.getPrimaryOwner().getDateOfBirth();

        Integer age = today.getYear() - birthDate.getYear();
        if (today.getMonth().getValue() < birthDate.getMonth().getValue()) age--;
        if (today.getMonth() == birthDate.getMonth() && today.getDayOfMonth() < birthDate.getDayOfMonth()) age--;

        if( age > 24 ) {
            account.setType(AccountType.REGULAR);
            account.setMinimumBalance(new Money(account.REGULAR_MINIMUM_BALANCE_AMOUNT, account.getMinimumBalance().getCurrency()));
            account.setMonthlyMaintenanceFee(new Money(account.REGULAR_MAINTENANCE_FEE_AMOUNT, account.getMonthlyMaintenanceFee().getCurrency()));
        }
    }
}
