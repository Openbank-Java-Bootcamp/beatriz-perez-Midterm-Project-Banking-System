package com.example.demo.controller.interfaces.users;

import com.example.demo.model.users.User;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserControllerInterface {

    void saveUser( User user );

}
