package com.example.demo.model.accounts;

import com.example.demo.model.aux.Money;
import jakarta.persistence.Entity;

@Entity
public class CreditCardAccount {

    private Double interestRate;

    private Money creditLimit;

    private Money minimumBalance;

    private Money penaltyFee;

}
