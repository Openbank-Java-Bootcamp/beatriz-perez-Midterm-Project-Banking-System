package com.example.demo.service.impl.security;

import com.example.demo.model.security.Role;
import com.example.demo.model.users.User;
import com.example.demo.repository.security.RoleRepository;
import com.example.demo.repository.users.UserRepository;
import com.example.demo.service.interfaces.security.RoleServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService implements RoleServiceInterface {

    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRepository userRepo;

    // Methods:

    // GET A LIST OF ALL EXISTING ROLES
    public List<Role> getAllRoles() {
        // Handle error:
        if(roleRepo.findAll().size() == 0) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "No elements to show" ); }
        // Show results
        log.info("Fetching all existing roles");
        return roleRepo.findAll();
    }

    // CREATE A NEW ROLE
    public Role createRole(Role role) {
        // Handle possible errors:
        if(roleRepo.findByName(role.getName()) != null) { throw new ResponseStatusException( HttpStatus.UNPROCESSABLE_ENTITY, "Element already exists" ); }
        // Save new role:
        log.info("Saving a new role {} to the DB", role.getName());
        return roleRepo.save(role);
    }

    // ASSIGN A ROLE TO A USER
    public void addRoleToUser(String username, String roleName) {
        Optional<User> user = userRepo.findByUsername(username);
        Optional<Role> role = roleRepo.findByName(roleName);
        // Handle possible errors:
        if(user.isEmpty()) { throw new ResponseStatusException( HttpStatus.NOT_FOUND, "User not found" ); }
        if(role.isEmpty()) { throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Role not found" ); }
        // Modify user's collection of roles:
        user.get().getRoles().add(role.get());
        // Save modified user
        userRepo.save(user.get());
    }

}
