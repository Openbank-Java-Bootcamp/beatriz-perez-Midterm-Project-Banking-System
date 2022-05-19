package com.example.demo.controller.interfaces;

import com.example.demo.DTO.RoleToUserDTO;
import com.example.demo.model.security.Role;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface RoleControllerInterface {

    List<Role> getAllRoles();

    void createRole( Role role );

    void addRoleToUser(RoleToUserDTO roleToUserDTO);

}
