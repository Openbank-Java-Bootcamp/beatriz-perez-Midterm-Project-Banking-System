package com.example.demo.controller.impl.users;

import com.example.demo.controller.interfaces.users.UserControllerInterface;
import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.users.ThirdParty;
import com.example.demo.model.users.User;
import com.example.demo.service.interfaces.users.AccountHolderServiceInterface;
import com.example.demo.service.interfaces.users.ThirdPartyServiceInterface;
import com.example.demo.service.interfaces.users.UserServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController implements UserControllerInterface {

    @Autowired
    private UserServiceInterface userService;
    @Autowired
    private AccountHolderServiceInterface accountHolderService;
    @Autowired
    private ThirdPartyServiceInterface thirdPartyService;

    // GET ENDPOINTS --------------------------------------------------------------------------------

    // Get a list of all active users
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user details by ID
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable(name = "id") String id) { return userService.getUserById(Long.parseLong(id)); }

    // Get a list of all active third parties
    @GetMapping("/users/third-party")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdParty> getAllThirdParties() {
        return thirdPartyService.getAllThirdParties();
    }

    // POST ENDPOINTS --------------------------------------------------------------------------------

    // Create ADMIN User
    @PostMapping("/users/admin-user")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid User user) { userService.createUser(user); }

    // Create AccountHolder user
    @PostMapping("/users/account-holder")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccountHolder(@RequestBody @Valid AccountHolder accountHolder) { accountHolderService.createAccountHolder(accountHolder); }

    // Create ThirdParty user
    @PostMapping("/users/third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public void createThirdParty(@RequestBody @Valid ThirdParty thirdParty) { thirdPartyService.createThirdParty(thirdParty); }


    // PUT ENDPOINTS ----------------------------------------------------------------------------------

    // Update ADMIN User
    @PutMapping("/users/admin-user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserById(@PathVariable String id, @RequestBody @Valid User user){
        userService.updateUserById(Long.parseLong(id), user);
    }

    // Update AccountHolder user
    @PutMapping("/users/account-holder/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccountHolderById(@PathVariable String id, @RequestBody @Valid AccountHolder accountHolder){
        accountHolderService.updateAccountHolderById(Long.parseLong(id), accountHolder);
    }

    // Update ThirdParty user
    @PutMapping("/users/third-party/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateThirdPartyById(@PathVariable String id, @RequestBody @Valid ThirdParty thirdParty){
        thirdPartyService.updateThirdPartyById(Long.parseLong(id), thirdParty);
    }

    // DELETE ENDPOINTS -------------------------------------------------------------------------------

    // Delete user by ID
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable String id){ userService.deleteUserById(Long.parseLong(id)); }

    // Delete third party by ID
    @DeleteMapping("/users/third-party/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteThirdPartyById(@PathVariable String id){ thirdPartyService.deleteThirdPartyById(Long.parseLong(id)); }

}
