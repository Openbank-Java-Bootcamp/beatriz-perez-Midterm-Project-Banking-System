package com.example.demo.model.users;

import com.example.demo.model.aux.Address;
import com.example.demo.model.aux.Money;
import com.example.demo.model.aux.Name;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
@Table(name = "account_holder")
public class AccountHolder extends User{

    // Attributes
    @NotNull(message = "User must have a dateOfBirth")
    private LocalDate dateOfBirth;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "streetAddress", column = @Column(name = "street_address")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "postal_code"))
    })
    @NotNull(message = "User must have a primaryAddress")
    private Address primaryAddress;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "streetAddress", column = @Column(name = "mailing_street_address")),
            @AttributeOverride(name = "city", column = @Column(name = "mailing_city")),
            @AttributeOverride(name = "country", column = @Column(name = "mailing_country")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mailing_postalCode"))
    })
    private Address mailingAddress;


    // Constructor
    public AccountHolder(Name name, String username, String password, String dateString, Address primaryAddress, Address mailingAddress) {
        super(name, username, password);
        this.dateOfBirth = LocalDate.parse(dateString);
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }
    public AccountHolder(Name name, String username, String password, String dateString, Address primaryAddress) {
        super(name, username, password);
        this.dateOfBirth = LocalDate.parse(dateString);
        this.primaryAddress = primaryAddress;
    }
}
