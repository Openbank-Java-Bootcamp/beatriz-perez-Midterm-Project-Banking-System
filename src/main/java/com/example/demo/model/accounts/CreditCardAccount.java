package com.example.demo.model.accounts;

import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.aux.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@Entity
@NoArgsConstructor
@Data
public class CreditCardAccount  extends Account{

    // Attributes
    @Column(name = "interest_rate")
    @DecimalMax(value = "0.2", message = "CreditCard accounts should have a maximum interest rate of 0.2")
    @DecimalMin(value = "0.1", message = "CreditCard accounts should have a minimum interest rate of 0.1")
    private BigDecimal interestRate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit")),
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit"))
    })
    private Money creditLimit;

    // Constructors
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money minimumBalance, BigDecimal interestRate, Money creditLimit) throws IllegalArgumentException {
        super(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance);
        this.interestRate = interestRate;
        BigDecimal maxCreditLimit = new BigDecimal("100000");
        BigDecimal minCreditLimit = new BigDecimal("100");
        // Check CREDIT LIMIT allowed range:
        if( creditLimit.getAmount().compareTo(maxCreditLimit) == 1 ) {
            super.setMinimumBalance(new Money(maxCreditLimit));
            throw new IllegalArgumentException("CreditCard accounts should have a maximum credit limit of 100000. Credit limit has been set to 100000.");
        }
        if( creditLimit.getAmount().compareTo(minCreditLimit) == -1 ) {
            super.setMinimumBalance(new Money(minCreditLimit));
            throw new IllegalArgumentException("CreditCard accounts should have a minimum credit limit of 100. Credit limit has been set to 100.");
        }
        this.creditLimit = creditLimit;
    }
    // default creditLimit
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money minimumBalance, BigDecimal interestRate) {
        this(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, interestRate, new Money(new BigDecimal("100")));
    }
    // default interestRate
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money minimumBalance, Money creditLimit) {
        this(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance,  new BigDecimal("0.2"), creditLimit);
    }
    // default interestRate & creditLimit
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money minimumBalance) {
        this(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, new Money(new BigDecimal("100")));
    }

}
