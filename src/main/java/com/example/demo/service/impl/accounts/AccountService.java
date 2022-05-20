package com.example.demo.service.impl.accounts;

import com.example.demo.DTO.ThirdPartyTransactionDTO;
import com.example.demo.model.accounts.Account;
import com.example.demo.model.secondary.Money;
import com.example.demo.model.users.ThirdParty;
import com.example.demo.repository.accounts.AccountRepository;
import com.example.demo.repository.users.ThirdPartyRepository;
import com.example.demo.service.interfaces.accounts.AccountServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements AccountServiceInterface {

    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private ThirdPartyRepository thirdPartyRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Methods

    // GET A LIST OF ALL EXISTING ACCOUNTS
    public List<Account> getAllAccounts() {
        // Handle possible errors:
        if(accountRepo.findAll().size() == 0) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "No elements to show" ); }
        // Return results
        log.info("Fetching all accounts");
        return accountRepo.findAll();
    }

    // GET AN ACCOUNT'S DETAILS BY ACCOUNT NUMBER
    public Account getAccountByNumber(Long accountNumber) {
        // Handle possible errors:
        if(accountRepo.findById(accountNumber).isEmpty()){ throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account found with the specified number"); }
        // Return user:
        log.info("Fetching account information");
        return accountRepo.findById(accountNumber).get();
    }

    // MODIFY AN ACCOUNTS BALANCE BY ACCOUNT NUMBER
    public void updateAccountBalance(Long accountNumber, BigDecimal newBalanceAmount) {
        Optional<Account> account = accountRepo.findById(accountNumber);
        // Handle possible errors:
        if(account.isEmpty()){ throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account found with the specified number"); }
        // Update account:
        log.info("Updating balance of account");
        Money newBalance = new Money( newBalanceAmount, account.get().getBalance().getCurrency() );
        account.get().setBalance(newBalance);
        accountRepo.save(account.get());
    }

    // MODIFY AN ACCOUNTS BALANCE OPERATING AS A THIRD PARTY

    /*

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof UserDetails) {
      String username = ((UserDetails)principal).getUsername();
    } else {
      String username = principal.toString();
    }

     */

    public void operateAsThirdParty(ThirdPartyTransactionDTO transactionDto) {
        Optional<ThirdParty> thirdParty = thirdPartyRepo.findByUsername(transactionDto.getUsername());
        Optional<Account> account = accountRepo.findById(transactionDto.getAccountNumber());
        String transactionCurrencyCode = transactionDto.getCurrencyCode();
        // Handle possible errors:
        // - Third Party user errors
        if(thirdParty.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No third party user found with the specified username");
        }
        if( !passwordEncoder.matches(transactionDto.getPassword(), thirdParty.get().getPassword()) ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Third-party password in database does not match the specified password");
        }
        // - Account errors
        if(account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account found with the specified number");
        }
        if(!passwordEncoder.matches(transactionDto.getAccountSecretKey(), account.get().getSecretKey()) ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account key does not match the specified key");
        }
        if(!account.get().getBalance().getCurrency().getCurrencyCode().equals(transactionCurrencyCode)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account currency does not match the request currency");
        }
        // Update account:
        log.info("Updating balance of account");
        account.get().getBalance().increaseAmount(transactionDto.getAmount());
        accountRepo.save(account.get());
    }

    // DELETE AN ACCOUNT BY ACCOUNT NUMBER
    public void deleteAccountByNumber(Long accountNumber) {
        Optional<Account> account = accountRepo.findById(accountNumber);
        // Handle possible errors:
        if(account.isEmpty()){ throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account found with the specified number"); }
        // Delete account:
        log.info("Deleting account");
        accountRepo.delete(account.get());
    }

}
