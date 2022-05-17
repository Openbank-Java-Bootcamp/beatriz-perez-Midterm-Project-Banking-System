package com.example.demo.service.impl.security;

import com.example.demo.model.security.Role;
import com.example.demo.model.users.User;
import com.example.demo.repository.security.RoleRepository;
import com.example.demo.repository.users.UserRepository;
import com.example.demo.service.interfaces.security.RoleServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService implements RoleServiceInterface {

    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRepository userRepo;

    public Role saveRole(Role role) {
        log.info("Saving a new role {} to the DB", role.getName());
        return roleRepo.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
        userRepo.save(user);
    }

}
