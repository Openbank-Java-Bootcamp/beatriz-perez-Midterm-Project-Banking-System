package com.example.demo.model.accounts;

import com.example.demo.enums.Status;
import com.example.demo.model.User;
import com.example.demo.model.aux.Money;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Optional;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    private Date creationDate;

    private String secretKey; // HASHED <-----------------------------------

    private User primaryOwner;

    private Optional<User> secondaryOwner;

    private Status status;

    private Money balance;

}
