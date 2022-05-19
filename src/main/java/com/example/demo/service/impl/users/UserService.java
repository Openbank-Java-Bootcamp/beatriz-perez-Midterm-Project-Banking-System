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

    // Methods
    public User saveUser(User user) {
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
