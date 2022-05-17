package com.example.demo.controller.interfaces.security;

import com.example.demo.DTO.RoleToUserDTO;
import com.example.demo.model.security.Role;
import org.springframework.web.bind.annotation.RequestBody;

public interface RoleControllerInterface {

    void saveRole( Role role );
    void addRoleToUser(RoleToUserDTO roleToUserDTO);

}
