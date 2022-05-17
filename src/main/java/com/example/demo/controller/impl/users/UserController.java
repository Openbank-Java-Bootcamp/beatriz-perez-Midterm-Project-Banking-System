package com.example.demo.controller.impl.users;

import com.example.demo.model.users.User;
import com.example.demo.service.interfaces.security.RoleServiceInterface;
import com.example.demo.service.interfaces.users.UserServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServiceInterface userService;

    // POST ENDPOINTS
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody @Valid User user) {
        userService.saveUser(user);
    }




}
