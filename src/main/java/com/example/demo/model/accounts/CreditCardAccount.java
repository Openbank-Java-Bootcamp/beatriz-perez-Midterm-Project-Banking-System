package com.example.demo.model.accounts;

import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.users.User;
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
    private Double interestRate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit")),
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit"))
    })
    private Money creditLimit;

    // Constructors
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner, Money balance, Money minimumBalance, Money penaltyFee, Double interestRate, Money creditLimit) {
        super(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, penaltyFee);
        this.interestRate = interestRate;
        // Savings accounts may be instantiated with a minimum balance of less than 1000 but no lower than 100
        if( creditLimit.getAmount().doubleValue() > 100000.0 ) {
            // MESSAGE <----------------------------------------------------------------------------------------------------- !
            // "CreditCard accounts should have a maximum credit limit of 100000"
            super.setMinimumBalance(new Money(new BigDecimal("100000")));
        }
        if( creditLimit.getAmount().doubleValue()< 100.0 ) {
            // MESSAGE <----------------------------------------------------------------------------------------------------- !
            // "CreditCard accounts should have a minimum credit limit of 100"
            super.setMinimumBalance(new Money(new BigDecimal("100")));
        }
        this.creditLimit = creditLimit;
    }
    // default creditLimit
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner, Money balance, Money minimumBalance, Money penaltyFee, Double interestRate) {
        this(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, penaltyFee, interestRate, new Money(new BigDecimal("100")));
    }
    // default interestRate
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner, Money balance, Money minimumBalance, Money penaltyFee, Money creditLimit) {
        this(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, penaltyFee, 0.2, creditLimit);
    }
    // default interestRate & creditLimit
    public CreditCardAccount(String secretKey, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner, Money balance, Money minimumBalance, Money penaltyFee) {
        this(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, penaltyFee, new Money(new BigDecimal("100")));
    }

}
