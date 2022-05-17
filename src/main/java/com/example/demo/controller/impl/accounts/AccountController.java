package com.example.demo.controller.impl.accounts;

import com.example.demo.controller.interfaces.accounts.AccountControllerInterface;
import com.example.demo.service.interfaces.accounts.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController implements AccountControllerInterface {

    @Autowired
    private AccountServiceInterface AccountService;

    // GET ENDPOINTS

    // POST ENDPOINTS

    // PUT ENDPOINTS

    // PATCH ENDPOINTS

    // DELETE ENDPOINTS


}
