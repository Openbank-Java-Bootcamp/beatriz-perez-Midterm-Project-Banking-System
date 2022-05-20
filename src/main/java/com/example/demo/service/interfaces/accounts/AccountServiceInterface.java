package com.example.demo.service.interfaces.accounts;

import com.example.demo.DTO.ThirdPartyTransactionDTO;
import com.example.demo.DTO.TransferDTO;
import com.example.demo.model.accounts.Account;
import com.example.demo.model.users.AccountHolder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountServiceInterface {

    // GET A LIST OF ALL EXISTING ACCOUNTS
    List<Account> getAllAccounts();

    // GET A LIST OF ALL EXISTING ACCOUNTS BY OWNER ID
    List<Account> getAllAccountsByOwner(Long id);

    // GET A LIST OF ALL ACCOUNTS BY ID - FOR LOGGED ACCOUNT HOLDER
    List<Account> getAllMyAccounts();

    // GET AN ACCOUNT'S DETAILS BY ACCOUNT NUMBER
    public Account getAccountByNumber(Long accountNumber);

    // GET MY ACCOUNT'S DETAILS BY ACCOUNT NUMBER
    Account getMyAccountByNumber(Long accountNumber);

    // MODIFY AN ACCOUNTS BALANCE BY ACCOUNT NUMBER
    void updateAccountBalance(Long accountNumber, BigDecimal newBalanceAmount);

    // MODIFY AN ACCOUNTS BALANCE OPERATING AS A THIRD PARTY
    void operateAsThirdParty(ThirdPartyTransactionDTO transactionDto);

    // TRANSFER MONEY AS AN ACCOUNT HOLDER
    void transferMoney(TransferDTO transferDto);

    // DELETE AN ACCOUNT BY ACCOUNT NUMBER
    void deleteAccountByNumber(Long accountNumber);

    // GET ACCOUNT OWNER FROM AUTHENTICATION
    Optional<AccountHolder> getOwnerFromAuthentication();

    // APPLY PENALTY FEE
    boolean checkPenaltyAlreadyApplied(Account account);
    void applyPenaltyFeeIfApplicable(boolean wasPenaltyAlreadyApplied , Account account);

    }
