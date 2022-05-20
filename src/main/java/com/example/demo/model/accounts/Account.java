package com.example.demo.model.accounts;

import com.example.demo.enums.Status;
import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.secondary.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Entity
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "account")
public class Account {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "secret_key")
    @NotEmpty(message = "You must have a secret key")
    private String secretKey;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "primary_owner")
    @NotNull(message = "Accounts must have an owner")
    private AccountHolder primaryOwner;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "secondary_owner")
    private AccountHolder secondaryOwner;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "balance_currency"))
    })
    @NotNull(message = "Accounts must have a balance")
    private Money balance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
    })
    @NotNull(message = "Accounts must have a minimum balance")
    private Money minimumBalance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee")),
            @AttributeOverride(name = "currency", column = @Column(name = "penalty_fee_currency"))
    })
    private Money penaltyFee;

    // Default values
    private static final BigDecimal FIXED_PENALTY_FEE_AMOUNT = new BigDecimal("40");


    // Constructor
    public Account(String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal minimumBalanceAmount, BigDecimal balanceAmount, Currency currency) {
        this.creationDate = LocalDate.now(); // Current date
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.status = Status.ACTIVE; // All accounts are active when created
        this.balance = new Money(balanceAmount, currency);
        this.minimumBalance = new Money(minimumBalanceAmount, currency); // MinimumBalance will have the same currency as the balance
        this.penaltyFee = new Money(FIXED_PENALTY_FEE_AMOUNT, currency); // Penalty fee will have the same currency as the balance
    }
}
