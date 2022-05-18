package com.example.demo.model.users;

import com.example.demo.model.aux.Name;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "third_party")
public class ThirdParty {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "User must have a username")
    private String username;
    @NotEmpty(message = "You must have a password")
    private String password;

    // Constructor
    public ThirdParty(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
