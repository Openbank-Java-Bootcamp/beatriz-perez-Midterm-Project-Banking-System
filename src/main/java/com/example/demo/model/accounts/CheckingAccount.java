package com.example.demo.model.accounts;

import com.example.demo.enums.AccountType;
import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.aux.Money;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
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
    public CheckingAccount(String secretKey, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner, Money balance) {
        super(secretKey, primaryOwner, secondaryOwner, balance, new Money(new BigDecimal("250")));
        if( getAge(primaryOwner) < 24 ) {
            this.type = AccountType.STUDENT;
            super.setMinimumBalance(new Money(new BigDecimal("0")));
            this.monthlyMaintenanceFee = new Money(new BigDecimal("0"));
        } else {
            this.type = AccountType.REGULAR;
            this.monthlyMaintenanceFee = new Money(new BigDecimal("12"));
        }
    }

    private int getAge(AccountHolder primaryOwner) {
        Date today = new Date();
        Date birthDate = primaryOwner.getDateOfBirth();

        Integer age = today.getYear() - birthDate.getYear();
        if (today.getMonth() < birthDate.getMonth()) age--;
        if (today.getMonth() == birthDate.getMonth() && today.getDay() < birthDate.getDay()) age--;
        return age;
    }


}
