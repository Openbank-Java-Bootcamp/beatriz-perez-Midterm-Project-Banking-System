package com.example.demo.service.impl.security;

import com.example.demo.model.security.Role;
import com.example.demo.model.users.User;
import com.example.demo.repository.security.RoleRepository;
import com.example.demo.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRepository userRepo;

    public Role saveRole(Role role) {
        log.info("Saving a new role {} to the DB", role.getName());
        return roleRepo.save(role);
    }

    public void addRoleToUser(Long userId, String roleName) {
        Optional<User> user = userRepo.findById(userId);
        Role role = roleRepo.findByName(roleName);
        user.get().getRoles().add(role);
        userRepo.save(user.get());
    }

}
