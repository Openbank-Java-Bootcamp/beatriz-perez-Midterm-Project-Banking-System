package com.example.demo.model.accounts;

import com.example.demo.model.aux.Money;
import jakarta.persistence.Entity;

@Entity
public class CheckingAccount extends Account{

    private Money monthlyMaintenanceFee;

    private Money minimumBalance;

    private Money penaltyFee;

}
