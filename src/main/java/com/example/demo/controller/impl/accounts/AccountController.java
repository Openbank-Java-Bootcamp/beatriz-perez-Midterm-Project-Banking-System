package com.example.demo.controller.impl.accounts;

import com.example.demo.DTO.AccountBalanceOnlyDTO;
import com.example.demo.DTO.NewCheckingAccountDTO;
import com.example.demo.DTO.NewCreditCardAccountDTO;
import com.example.demo.DTO.NewSavingsAccountDTO;
import com.example.demo.controller.interfaces.accounts.AccountControllerInterface;
import com.example.demo.model.accounts.Account;
import com.example.demo.model.accounts.CheckingAccount;
import com.example.demo.model.accounts.CreditCardAccount;
import com.example.demo.model.accounts.SavingsAccount;
import com.example.demo.model.users.User;
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

    // Get a list of all active accounts
    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // Get account details by accountNumber
    @GetMapping("/accounts/{account-number}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountByNumber(@PathVariable(name = "account-number") String number) { return accountService.getAccountByNumber(Long.parseLong(number)); }


    // POST ENDPOINTS --------------------------------------------------------------------------------

    // Create a new Checking Account
    @PostMapping("/accounts/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCheckingAccount(@RequestBody @Valid NewCheckingAccountDTO accountDTO) { checkingService.createCheckingAccount(accountDTO); }

    // Create a new Credit Card Account
    @PostMapping("/accounts/credit-card")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCreditCardAccount(@RequestBody @Valid NewCreditCardAccountDTO accountDTO) { creditCardService.createCreditCardAccount(accountDTO); }

    // Create a new Savings Account
    @PostMapping("/accounts/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSavingsAccount(@RequestBody @Valid NewSavingsAccountDTO accountDTO) { savingsService.createSavingsAccount(accountDTO); }


    // PUT ENDPOINTS ----------------------------------------------------------------------------------

    // PATCH ENDPOINTS --------------------------------------------------------------------------------

    // Modify an account's balance
    @PatchMapping("/accounts/{account-number}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccountBalance(@PathVariable(name = "account-number") String number, @RequestBody AccountBalanceOnlyDTO accountDto) {
        accountService.updateAccountBalance(Long.parseLong(number), accountDto.getNewBalanceAmount());
    }


    // DELETE ENDPOINTS -------------------------------------------------------------------------------

    // Delete account by accountNumber
    @DeleteMapping("/accounts/{account-number}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccountByNumber(@PathVariable(name = "account-number") String number) { accountService.deleteAccountByNumber(Long.parseLong(number)); }

}
