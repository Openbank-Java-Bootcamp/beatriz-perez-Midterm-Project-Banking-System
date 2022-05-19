package com.example.demo.controller.impl.users;

import com.example.demo.controller.interfaces.users.UserControllerInterface;
import com.example.demo.model.users.User;
import com.example.demo.service.interfaces.users.UserServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController implements UserControllerInterface {

    @Autowired
    private UserServiceInterface userService;

    // POST ENDPOINTS

    // Create
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody @Valid User user) {
        userService.saveUser(user);
    }




}
