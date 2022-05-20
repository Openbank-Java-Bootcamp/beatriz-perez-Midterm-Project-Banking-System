package com.example.demo.model.accounts;

import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.secondary.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;
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
            @AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency"))
    })
    private Money creditLimit;

    // Default values
    private static final BigDecimal DEFAULT_INTEREST_RATE = new BigDecimal("0.2");
    private static final BigDecimal DEFAULT_CREDIT_LIMIT_AMOUNT = new BigDecimal("100");


    // Constructors
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal minimumBalanceAmount, BigDecimal balanceAmount, Currency currency, BigDecimal interestRate, Money creditLimit) {
        super( secretKey, primaryOwner, secondaryOwner, minimumBalanceAmount, balanceAmount, currency );
        this.interestRate = interestRate;
        this.creditLimit = creditLimit;
    }
    // default creditLimit
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal minimumBalanceAmount, BigDecimal balanceAmount, Currency currency, BigDecimal interestRate) {
        this(secretKey,primaryOwner, secondaryOwner, minimumBalanceAmount, balanceAmount, currency, interestRate, new Money(DEFAULT_CREDIT_LIMIT_AMOUNT, currency));
    }
    // default interestRate
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal minimumBalanceAmount, BigDecimal balanceAmount, Currency currency, Money creditLimit) {
        this(secretKey,primaryOwner, secondaryOwner, minimumBalanceAmount, balanceAmount, currency, DEFAULT_INTEREST_RATE, creditLimit);
    }
    // default creditLimit & interestRate
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal minimumBalanceAmount, BigDecimal balanceAmount, Currency currency) {
        this(secretKey,primaryOwner, secondaryOwner, minimumBalanceAmount, balanceAmount, currency, DEFAULT_INTEREST_RATE, new Money(DEFAULT_CREDIT_LIMIT_AMOUNT, currency));
    }
}
