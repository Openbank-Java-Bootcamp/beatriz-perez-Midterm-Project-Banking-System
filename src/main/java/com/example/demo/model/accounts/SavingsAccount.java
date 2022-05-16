package com.example.demo.model.accounts;

import com.example.demo.model.User;
import com.example.demo.model.aux.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@NoArgsConstructor
@Data
public class SavingsAccount  extends Account{

    // Attributes
    @Column(name = "interest_rate")
    private Double interestRate;

    // Constructor
    public SavingsAccount(String secretKey, User primaryOwner, Optional<User> secondaryOwner, Money balance, Money minimumBalance, Money penaltyFee, Double interestRate) {
        super(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, penaltyFee);
        this.interestRate = interestRate;
    }
}
