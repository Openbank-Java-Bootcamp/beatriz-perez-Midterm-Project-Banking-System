package com.example.demo.controller.impl.accounts;

import com.example.demo.controller.interfaces.accounts.CheckingAccountControllerInterface;
import com.example.demo.service.interfaces.accounts.CheckingAccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CheckingAccountController implements CheckingAccountControllerInterface {

    @Autowired
    private CheckingAccountServiceInterface CheckingAccountService;

    // POST ENDPOINTS


}
