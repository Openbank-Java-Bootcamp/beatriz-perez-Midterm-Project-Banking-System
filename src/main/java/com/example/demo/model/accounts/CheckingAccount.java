package com.example.demo.model.accounts;

import com.example.demo.enums.AccountType;
import com.example.demo.model.User;
import com.example.demo.model.aux.Money;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@NoArgsConstructor
@Data
public class CheckingAccount extends Account{

    // Attributes
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee")),
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_maintenance_fee_currency"))
    })
    private Money monthlyMaintenanceFee;

    private AccountType type;

    // Constructor
    public CheckingAccount(String secretKey, User primaryOwner, Optional<User> secondaryOwner, Money balance, Money minimumBalance, Money penaltyFee, Money monthlyMaintenanceFee, AccountType type) {
        super(secretKey, primaryOwner, secondaryOwner, balance, minimumBalance, penaltyFee);
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.type = type;
    }
}
