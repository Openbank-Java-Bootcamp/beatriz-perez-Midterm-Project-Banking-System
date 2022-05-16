package com.example.demo.model.accounts;

import com.example.demo.model.User;
import com.example.demo.model.aux.Money;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@NoArgsConstructor
@Data
public class CreditCardAccount  extends Account{

    // Attributes
    @Column(name = "interest_rate")
    private Double interestRate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit")),
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit"))
    })
    private Money creditLimit;

    // Constructor
    public CreditCardAccount(String secretKey, User primaryOwner, Optional<User> secondaryOwner, Money balance, Money minimumBalance, Money penaltyFee, Double interestRate, Money creditLimit) {
        super(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, penaltyFee);
        this.interestRate = interestRate;
        this.creditLimit = creditLimit;
    }
}
