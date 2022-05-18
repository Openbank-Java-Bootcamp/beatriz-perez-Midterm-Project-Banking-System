package com.example.demo.model.accounts;

import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.aux.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
            @AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency"))
    })
    private Money creditLimit;

    // Default values
    private static final BigDecimal DEFAULT_INTEREST_RATE = new BigDecimal("0.2");
    private static final Money DEFAULT_CREDIT_LIMIT = new Money(new BigDecimal("100"));


    // Constructors
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money minimumBalance, BigDecimal interestRate, Money creditLimit) {
        super(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance);
        this.interestRate = interestRate;
        this.creditLimit = creditLimit;
    }
    // default creditLimit
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money minimumBalance, BigDecimal interestRate) {
        this(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, interestRate, DEFAULT_CREDIT_LIMIT);
    }
    // default interestRate
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money minimumBalance, Money creditLimit) {
        this(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, DEFAULT_INTEREST_RATE, creditLimit);
    }
    // default interestRate & creditLimit
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money minimumBalance) {
        this(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, DEFAULT_CREDIT_LIMIT);
    }

}
