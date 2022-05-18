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

    // Constructors
    public SavingsAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money minimumBalance, BigDecimal interestRate) {
        super( secretKey, primaryOwner, secondaryOwner, balance, minimumBalance );
        this.interestRate = interestRate;
    }
    // default minimumBalance:
    public SavingsAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, BigDecimal interestRate) {
        this( secretKey, primaryOwner, secondaryOwner, balance, new Money(new BigDecimal("1000")), interestRate );
    }
    // default interest rate:
    public SavingsAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money minimumBalance) {
        this( secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, new BigDecimal("0.0025") );
    }
    // default interest rate & minimumBalance
    public SavingsAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance) {
        this( secretKey, primaryOwner, secondaryOwner, balance, new Money(new BigDecimal("1000")) );
    }
}
