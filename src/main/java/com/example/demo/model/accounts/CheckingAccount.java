package com.example.demo.model.accounts;

import com.example.demo.enums.AccountType;
import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.secondary.Money;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

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

    // Default values
    private static final BigDecimal STUDENT_MINIMUM_BALANCE_AMOUNT = new BigDecimal("0");
    private static final BigDecimal STUDENT_MAINTENANCE_FEE_AMOUNT = new BigDecimal("0");

    public static final BigDecimal REGULAR_MINIMUM_BALANCE_AMOUNT = new BigDecimal("250");
    public static final BigDecimal REGULAR_MAINTENANCE_FEE_AMOUNT = new BigDecimal("12");


    // Constructor
    public CheckingAccount(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal balanceAmount, Currency currency) {
        super( secretKey, primaryOwner, secondaryOwner, STUDENT_MINIMUM_BALANCE_AMOUNT, balanceAmount, currency );
        this.monthlyMaintenanceFee = new Money(STUDENT_MAINTENANCE_FEE_AMOUNT, currency);
        this.type = AccountType.STUDENT;
    }
}
