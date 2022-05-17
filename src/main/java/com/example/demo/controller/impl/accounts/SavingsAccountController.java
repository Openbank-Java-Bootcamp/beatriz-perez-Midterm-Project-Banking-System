package com.example.demo.controller.impl.accounts;

import com.example.demo.controller.interfaces.accounts.SavingsAccountControllerInterface;
import com.example.demo.service.interfaces.accounts.SavingsAccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SavingsAccountController implements SavingsAccountControllerInterface {

    @Autowired
    private SavingsAccountServiceInterface SavingsAccountService;

    // POST ENDPOINTS


}
