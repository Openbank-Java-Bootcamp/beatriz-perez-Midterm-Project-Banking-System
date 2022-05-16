package com.example.demo.model.accounts;

import com.example.demo.model.aux.Money;
import jakarta.persistence.Entity;

@Entity
public class SavingsAccount {

    private Double interestRate;

    private Money minimumBalance;

    private Money penaltyFee;

}
