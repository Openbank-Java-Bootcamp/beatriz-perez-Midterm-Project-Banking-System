package com.example.demo.model.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@Data
@Table(name = "account_holder")
public class AccountHolder extends User{

    // Attributes
    private Date dateOfBirth;

    // Constructors
    public AccountHolder(String username, String password, Date dateOfBirth) {
        super(username, password);
        this.dateOfBirth = dateOfBirth;
    }
}
