package com.example.demo.model.accounts;

import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.aux.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
public class SavingsAccount  extends Account{

    // Attributes
    @Column(name = "interest_rate")
    @DecimalMax(value = "0.5", message = "Savings accounts should have a maximum interest rate of 0.5")
    @DecimalMin(value = "0", message = "CreditCard accounts should have a positive interest rate")
    private BigDecimal interestRate;

    // Default values
    private static final BigDecimal DEFAULT_INTEREST_RATE = new BigDecimal("0.0025");
    private static final BigDecimal DEFAULT_MINIMUM_BALANCE_AMOUNT = new BigDecimal("1000");

    // Constructors
    public SavingsAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money minimumBalance, BigDecimal interestRate) {
        super( secretKey, primaryOwner, secondaryOwner, balance, new Money(DEFAULT_MINIMUM_BALANCE_AMOUNT, balance.getCurrency()) );
        this.interestRate = interestRate;
    }
    // default minimumBalance:
    public SavingsAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, BigDecimal interestRate) {
        this( secretKey, primaryOwner, secondaryOwner, balance, new Money(DEFAULT_MINIMUM_BALANCE_AMOUNT, balance.getCurrency()), interestRate );
    }
    // default interest rate:
    public SavingsAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money minimumBalance) {
        this( secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, DEFAULT_INTEREST_RATE );
    }
    // default interest rate & minimumBalance
    public SavingsAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance) {
        this( secretKey, primaryOwner, secondaryOwner, balance, new Money(DEFAULT_MINIMUM_BALANCE_AMOUNT, balance.getCurrency()) );
    }
}
