package com.example.demo.service.interfaces.users;

import com.example.demo.model.users.User;

import java.util.List;

public interface UserServiceInterface {

    // GET A LIST OF ALL EXISTING USERS
    List<User> getAllUsers();

    // GET A USER'S DETAILS BY ID
    User getUserById(Long id);

    // CREATE A NEW USER-ADMIN
    User createUser(User user);

    // UPDATE A USER BY ID
    void updateUserById(Long id, User user);

    // DELETE A USER BY ID
    void deleteUserById(Long id);

}
