package com.example.demo.model.accounts;

import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.users.User;
import com.example.demo.model.aux.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMax;
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
    private Double interestRate;

    // Constructors
    public SavingsAccount(String secretKey, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner, Money balance, Money minimumBalance, Money penaltyFee, Double interestRate) {
        super(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, penaltyFee);
        this.interestRate = interestRate;
        // Savings accounts may be instantiated with a minimum balance of less than 1000 but no lower than 100
        if( minimumBalance.getAmount().doubleValue() > 1000.0 ) {
            // MESSAGE <----------------------------------------------------------------------------------------------------- !
            super.setMinimumBalance(new Money(new BigDecimal("1000")));
        }
        if( minimumBalance.getAmount().doubleValue()< 100.0 ) {
            // MESSAGE <----------------------------------------------------------------------------------------------------- !
            super.setMinimumBalance(new Money(new BigDecimal("100")));
        }
    }
    // default minimumBalance:
    public SavingsAccount(String secretKey, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner, Money balance, Money penaltyFee, Double interestRate) {
        this(secretKey, primaryOwner, secondaryOwner, balance, new Money(new BigDecimal("1000")), penaltyFee, interestRate);
    }
    // default interest rate:
    public SavingsAccount(String secretKey, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner, Money balance, Money minimumBalance, Money penaltyFee) {
        this(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, penaltyFee, 0.0025);
    }
    // default interest rate & minimumBalance
    public SavingsAccount(String secretKey, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner, Money balance, Money penaltyFee) {
        this(secretKey, primaryOwner, secondaryOwner, balance, new Money(new BigDecimal("1000")), penaltyFee);
    }
}
