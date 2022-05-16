package com.example.demo.model.accounts;

import com.example.demo.enums.Status;
import com.example.demo.model.User;
import com.example.demo.model.aux.Money;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;

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
    private Date creationDate;

    @Column(name = "secret_key")
    private String secretKey; // HASHED <-----------------------------------

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "primary_owner")
    private User primaryOwner;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "secondary_owner")
    private User secondaryOwner;

    private Status status;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "balance_currency"))
    })
    private Money balance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
    })
    private Money minimumBalance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee")),
            @AttributeOverride(name = "currency", column = @Column(name = "penalty_fee_currency"))
    })
    private Money penaltyFee;

    // Constructor
    public Account(String secretKey, User primaryOwner, Optional<User> secondaryOwner, Money balance, Money minimumBalance, Money penaltyFee) {
        this.creationDate = new Date(); // Current date
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner.get();
        this.status = Status.ACTIVE; // All accounts are active when created
        this.balance = balance;
        this.minimumBalance = minimumBalance;
        this.penaltyFee = penaltyFee;
    }
}
