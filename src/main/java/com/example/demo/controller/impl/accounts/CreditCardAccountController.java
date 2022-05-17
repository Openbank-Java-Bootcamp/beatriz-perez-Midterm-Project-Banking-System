package com.example.demo.controller.impl.accounts;

import com.example.demo.controller.interfaces.accounts.CreditCardAccountControllerInterface;
import com.example.demo.service.interfaces.accounts.CreditCardAccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CreditCardAccountController implements CreditCardAccountControllerInterface {

    @Autowired
    private CreditCardAccountServiceInterface CreditCardAccountService;

    // POST ENDPOINTS


}
