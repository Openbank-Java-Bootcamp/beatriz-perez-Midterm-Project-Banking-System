package com.example.demo.controller.impl.users;

import com.example.demo.controller.interfaces.users.AccountHolderControllerInterface;
import com.example.demo.service.interfaces.users.AccountHolderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountHolderController implements AccountHolderControllerInterface {

    @Autowired
    private AccountHolderServiceInterface AccountHolderService;

    // POST ENDPOINTS


}
