package com.example.demo.controller.impl.security;

import com.example.demo.DTO.RoleToUserDTO;
import com.example.demo.controller.interfaces.security.RoleControllerInterface;
import com.example.demo.model.security.Role;
import com.example.demo.service.interfaces.security.RoleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RoleController implements RoleControllerInterface {

    @Autowired
    private RoleServiceInterface roleService;

    // POST ENDPOINTS
    @PostMapping("/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveRole(@RequestBody Role role) {
        roleService.saveRole(role);
    }

    @PostMapping("/roles/assign")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addRoleToUser(@RequestBody RoleToUserDTO roleToUserDTO) {
        roleService.addRoleToUser(roleToUserDTO.getUsername(), roleToUserDTO.getRoleName());
    }

}
