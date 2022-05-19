package com.example.demo.controller.impl;

import com.example.demo.DTO.RoleToUserDTO;
import com.example.demo.controller.interfaces.RoleControllerInterface;
import com.example.demo.model.security.Role;
import com.example.demo.service.interfaces.security.RoleServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController implements RoleControllerInterface {

    @Autowired
    private RoleServiceInterface roleService;

    // GET ENDPOINTS --------------------------------------------------------------------------------

    // Get a list of all existing roles:
    @GetMapping("/roles")
    @ResponseStatus(HttpStatus.OK)
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }


    // POST ENDPOINTS --------------------------------------------------------------------------------

    // Create a new role:
    @PostMapping("/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRole(@RequestBody @Valid Role role) {
        roleService.createRole(role);
    }

    // Assign a role to a user:
    @PostMapping("/roles/assign")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addRoleToUser(@RequestBody RoleToUserDTO roleToUserDTO) {
        roleService.addRoleToUser(roleToUserDTO.getUsername(), roleToUserDTO.getRoleName());
    }

}
