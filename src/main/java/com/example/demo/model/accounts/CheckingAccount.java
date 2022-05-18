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

    @Enumerated(EnumType.STRING)
    private AccountType type;

    // Constructor
    public CheckingAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance) {
        super( secretKey, primaryOwner, secondaryOwner, balance, new Money(new BigDecimal("0"), balance.getCurrency()));
        this.monthlyMaintenanceFee = new Money(new BigDecimal("0"), balance.getCurrency());
        this.type = AccountType.STUDENT;
    }
}
