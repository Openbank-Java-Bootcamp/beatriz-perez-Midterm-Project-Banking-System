package com.example.demo.controller.impl;

import com.example.demo.DTO.*;
import com.example.demo.controller.interfaces.AccountControllerInterface;
import com.example.demo.model.accounts.Account;
import com.example.demo.service.interfaces.accounts.AccountServiceInterface;
import com.example.demo.service.interfaces.accounts.CheckingAccountServiceInterface;
import com.example.demo.service.interfaces.accounts.CreditCardAccountServiceInterface;
import com.example.demo.service.interfaces.accounts.SavingsAccountServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController implements AccountControllerInterface {

    @Autowired
    private AccountServiceInterface accountService;
    @Autowired
    private CheckingAccountServiceInterface checkingService;
    @Autowired
    private CreditCardAccountServiceInterface creditCardService;
    @Autowired
    private SavingsAccountServiceInterface savingsService;

    // GET ENDPOINTS --------------------------------------------------------------------------------
    // for admin users ------------------------------------------------------------------------------

    // Get a list of all active accounts
    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // Get a list of all active accounts by owner ID (primary AND secondary owner)
    @GetMapping("/accounts/user/{owner-id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccountsByOwner(@PathVariable(name = "owner-id") String id ) { return accountService.getAllAccountsByOwner(Long.parseLong(id)); }

    // Get account details by accountNumber
    @GetMapping("/accounts/{account-number}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountByNumber(@PathVariable(name = "account-number") String number) { return accountService.getAccountByNumber(Long.parseLong(number)); }

    // GET ENDPOINTS --------------------------------------------------------------------------------
    // for account holder  users ----------------------------------------------------------------------

    // Get a list of all MY active accounts (primary AND secondary owner) as authenticated user
    @GetMapping("/account-holder/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllMyAccounts() { return accountService.getAllMyAccounts(); }

    // Get account details of one of MY accounts by accountNumber (primary AND secondary owner) as authenticated user
    @GetMapping("/account-holder/accounts/{account-number}")
    @ResponseStatus(HttpStatus.OK)
    public Account getMyAccountByNumber(@PathVariable(name = "account-number") String number) { return accountService.getMyAccountByNumber(Long.parseLong(number)); }


    // POST ENDPOINTS --------------------------------------------------------------------------------

    // Create a new Checking Account
    @PostMapping("/accounts/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCheckingAccount(@RequestBody @Valid NewAccountDTO accountDTO) { checkingService.createCheckingAccount(accountDTO); }

    // Create a new Credit Card Account
    @PostMapping("/accounts/credit-card")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCreditCardAccount(@RequestBody @Valid NewAccountDTO accountDTO) { creditCardService.createCreditCardAccount(accountDTO); }

    // Create a new Savings Account
    @PostMapping("/accounts/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSavingsAccount(@RequestBody @Valid NewAccountDTO accountDTO) { savingsService.createSavingsAccount(accountDTO); }


    // PUT ENDPOINTS ----------------------------------------------------------------------------------

    // PATCH ENDPOINTS --------------------------------------------------------------------------------

    // Modify an account's balance
    @PatchMapping("/accounts/{account-number}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccountBalance(@PathVariable(name = "account-number") String number, @RequestBody AccountBalanceOnlyDTO accountDto) {
        accountService.updateAccountBalance(Long.parseLong(number), accountDto.getNewBalanceAmount());
    }

    // Operate as Third Party
    @PatchMapping("/accounts/third-party-transaction")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void operateAsThirdParty(@RequestBody ThirdPartyTransactionDTO transactionDto) {
        accountService.operateAsThirdParty(transactionDto);
    }

    // Transfer money as an Account Holder
    @PatchMapping("/account-holder/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transferMoney(@RequestBody TransferDTO transferDto) {
        accountService.transferMoney(transferDto);
    }

    // DELETE ENDPOINTS -------------------------------------------------------------------------------

    // Delete account by accountNumber
    @DeleteMapping("/accounts/{account-number}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccountByNumber(@PathVariable(name = "account-number") String number) { accountService.deleteAccountByNumber(Long.parseLong(number)); }

}
