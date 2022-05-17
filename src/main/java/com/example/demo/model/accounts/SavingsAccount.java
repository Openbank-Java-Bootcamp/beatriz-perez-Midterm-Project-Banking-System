package com.example.demo.model.accounts;

import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.users.User;
import com.example.demo.model.aux.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

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
    public SavingsAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money minimumBalance, BigDecimal interestRate) throws IllegalArgumentException {
        super( secretKey, primaryOwner, secondaryOwner, balance, minimumBalance );
        this.interestRate = interestRate;
        BigDecimal maxMinimumBalance = new BigDecimal("1000");
        BigDecimal minMinimumBalance = new BigDecimal("100");
        // Check MINIMUM BALANCE allowed range:
        if( minimumBalance.getAmount().compareTo(maxMinimumBalance) == 1 ) {
            super.setMinimumBalance(new Money(maxMinimumBalance));
            throw new IllegalArgumentException("Savings accounts should have a minimum balance of 1000 as a maximum. Minimum balance has been set to 1000.");
        }
        if( minimumBalance.getAmount().compareTo(minMinimumBalance) == -1 ) {
            super.setMinimumBalance(new Money(minMinimumBalance));
            throw new IllegalArgumentException("Savings accounts should have a minimum balance of 100 as a minimum. Minimum balance has been set to 100.");
        }
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
