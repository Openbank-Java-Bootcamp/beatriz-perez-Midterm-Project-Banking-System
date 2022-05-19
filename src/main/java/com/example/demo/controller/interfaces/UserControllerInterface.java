package com.example.demo.controller.interfaces;

import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.users.ThirdParty;
import com.example.demo.model.users.User;

import java.util.List;

public interface UserControllerInterface {

    // Get a list of all active users:
    List<User> getAllUsers();
    // Get user details by ID:
    User getUserById(String id);
    // Get a list of all active third parties:
    List<ThirdParty> getAllThirdParties();

    // Create ADMIN user:
    void createUser( User user );
    // Create AccountHolder user
    void createAccountHolder(AccountHolder accountHolder);
    // Create ThirdParty user
    void createThirdParty(ThirdParty thirdParty);

    // Update ADMIN User
    void updateUserById(String id, User user);
    // Update AccountHolder user
    void updateAccountHolderById(String id, AccountHolder accountHolder);
    // Update ThirdParty user
    void updateThirdPartyById(String id, ThirdParty thirdParty);

    // Delete user by ID
    void deleteUserById(String id);
    // Delete third party by ID
    void deleteThirdPartyById(String id);

}
