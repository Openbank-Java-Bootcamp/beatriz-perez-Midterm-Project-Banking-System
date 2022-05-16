package com.example.demo.model.accounts;

import com.example.demo.enums.AccountType;
import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.users.User;
import com.example.demo.model.aux.Money;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    public CheckingAccount(String secretKey, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner, Money balance, Money penaltyFee) {
        super(secretKey, primaryOwner, secondaryOwner, balance, new Money(new BigDecimal("250")), penaltyFee);
        if( getAge(primaryOwner) < 24 ) {
            super.setMinimumBalance(new Money(new BigDecimal("0")));
            this.monthlyMaintenanceFee = new Money(new BigDecimal("0"));
            this.type = AccountType.STUDENT;
        } else {
            this.monthlyMaintenanceFee = new Money(new BigDecimal("12"));
            this.type = AccountType.REGULAR;
        }
    }

    private int getAge(AccountHolder primaryOwner) {

        // CALCULATE AGE <-----------------------------------------------

        return age;
    }


}
