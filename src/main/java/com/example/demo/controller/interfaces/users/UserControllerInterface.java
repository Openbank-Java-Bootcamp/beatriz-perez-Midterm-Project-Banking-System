package com.example.demo.controller.interfaces.users;

import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.users.ThirdParty;
import com.example.demo.model.users.User;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserControllerInterface {

    // Get a list of all active users:
    List<User> getAllUsers();
    // Get user details by ID:
    User getUserById(Long id);

    // Create ADMIN user:
    void createUser( User user );
    // Create AccountHolder user
    void createAccountHolder(AccountHolder accountHolder);
    // Create ThirdParty user
    void createThirdParty(ThirdParty thirdParty);

    // Update ADMIN User
    void updateUserById(Long id, User user);
    // Update AccountHolder user

    // Update ThirdParty user


    // Delete user by ID
    void deleteUserById(Long id);

}
