package com.example.demo.service.impl.users;

import com.example.demo.model.users.User;
import com.example.demo.repository.users.UserRepository;
import com.example.demo.service.interfaces.security.RoleServiceInterface;
import com.example.demo.service.interfaces.users.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserServiceInterface, UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleServiceInterface roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Methods:

    // GET A LIST OF ALL EXISTING USERS
c
    // GET A USER'S DETAILS BY ID
    public User getUserById(Long id) {
        // Handle possible errors:
        if(userRepo.findById(id).isEmpty()){ throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found with the specified ID"); }
        // Return user:
        log.info("Fetching user information");
        return userRepo.findById(id).get();
    }


    // CREATE A NEW USER-ADMIN
    public User createUser(User user) {
        // Handle possible errors:
        if(userRepo.findByUsername(user.getUsername()) != null) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "Element already exists" ); }
        // Encrypt secret key:
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Save new user:
        log.info("Saving a new user {} in the DB", user.getUsername());
        User dbUser = userRepo.save(user);
        // Add ADMIN role to user (all User class instances are ADMIN users):
        roleService.addRoleToUser(user.getUsername(), "ROLE_ADMIN");
        return dbUser;
    }

    // UPDATE A USER-ADMIN BY ID
    public void updateUserById(Long id, User user) {
        Optional<User> oldUser = userRepo.findById(id);
        // Handle possible errors:
        if(oldUser.isEmpty()){ throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found with the specified ID"); }
        // Update user:
        log.info("Updating user");
        user.setId(oldUser.get().getId());
        userRepo.save(user);
    }

    // DELETE A USER BY ID
    public void deleteUserById(Long id) {
        Optional<User> user = userRepo.findById(id);
        // Handle possible errors:
        if(user.isEmpty()){ throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found with the specified ID"); }
        // CHECK FOR ACCOUNTS
        // Delete user:
        log.info("Deleting user");
        userRepo.delete(user.get());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null)  {
            log.error("User not found in the DataBase");
            throw new UsernameNotFoundException("User not found in the DataBase");
        } else {
            log.error("User is found in the DataBase: {}", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }
}
