package com.example.demo.service.impl.users;

import com.example.demo.model.security.Role;
import com.example.demo.model.users.User;
import com.example.demo.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepo;

    // Methods
    public User saveUser(User user) {
        // Handle possible errors:
        if(userRepo.findByUsername(user.getUsername()) != null) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "Element already exists" ); }
        // Save new user:
        log.info("Saving a new user {} in the DB", user.getUsername());
        return userRepo.save(user);
    }

}
