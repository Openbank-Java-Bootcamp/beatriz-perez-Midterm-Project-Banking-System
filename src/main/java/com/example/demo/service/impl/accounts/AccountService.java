package com.example.demo.service.impl.accounts;

import com.example.demo.DTO.ThirdPartyTransactionDTO;
import com.example.demo.model.accounts.Account;
import com.example.demo.model.secondary.Money;
import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.users.ThirdParty;
import com.example.demo.repository.accounts.AccountRepository;
import com.example.demo.repository.users.AccountHolderRepository;
import com.example.demo.repository.users.ThirdPartyRepository;
import com.example.demo.repository.users.UserRepository;
import com.example.demo.service.interfaces.accounts.AccountServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements AccountServiceInterface {

    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AccountHolderRepository accountHolderRepo;
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

    // GET A LIST OF ALL EXISTING ACCOUNTS BY OWNER ID
    public List<Account> getAllAccountsByOwner(Long id) {
        // Handle possible errors:
        if(accountHolderRepo.findById(id).isEmpty()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account holder found with the specified ID"); }
        log.info("Fetching all accounts");
        List<Account> accounts = new ArrayList<>();
        accounts.addAll(accountRepo.findAllByPrimaryOwner(accountHolderRepo.findById(id).get()));
        accounts.addAll(accountRepo.findAllBySecondaryOwner(accountHolderRepo.findById(id).get()));
        if(accounts.size() == 0) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "No elements to show" ); }
        // Return results
        return accounts;
    }

    // GET A LIST OF ALL ACCOUNTS BY ID - FOR LOGGED ACCOUNT HOLDER
    public List<Account> getAllMyAccounts() {
        // Get user authentication details:
        Optional<AccountHolder> owner = getOwnerFromAuthentication();
        // Handle possible errors:
        if(owner.isEmpty()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account holder found with the specified username"); }
        List<Account> accounts = getAllAccountsByOwner(owner.get().getId());
        if(accounts.size() == 0) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "No elements to show" ); }
        // Return results
        return accounts;
    }

    // GET AN ACCOUNT'S DETAILS BY ACCOUNT NUMBER
    public Account getAccountByNumber(Long accountNumber) {
        // Handle possible errors:
        if(accountRepo.findById(accountNumber).isEmpty()){ throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account found with the specified number"); }
        // Return user:
        log.info("Fetching account information");
        return accountRepo.findById(accountNumber).get();
    }

    // GET MY ACCOUNT'S DETAILS BY ACCOUNT NUMBER
    public Account getMyAccountByNumber(Long accountNumber) {
        // Get user authentication details:
        Optional<AccountHolder> owner = getOwnerFromAuthentication();
        Optional<Account> account = accountRepo.findById(accountNumber);
        // Handle possible errors:
        if(owner.isEmpty()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account holder found with the specified username"); }
        if(account.isEmpty()){ throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account found with the specified number"); }
        if(!account.get().getPrimaryOwner().getUsername().equals(owner.get().getUsername())) {
            if(account.get().getSecondaryOwner() == null || !account.get().getSecondaryOwner().getUsername().equals(owner.get().getUsername()) ) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account's owner username does not match your request username");
            }
        }
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

    // GET ACCOUNT OWNER FROM AUTHENTICATION
    public Optional<AccountHolder> getOwnerFromAuthentication() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return accountHolderRepo.findByUsername(username);
    }

}
