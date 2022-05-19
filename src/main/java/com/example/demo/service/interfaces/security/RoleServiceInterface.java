package com.example.demo.service.interfaces.security;

import com.example.demo.model.security.Role;

import java.util.List;

public interface RoleServiceInterface {

    List<Role> getAllRoles();

    Role createRole(Role role);

    void addRoleToUser(String username, String roleName);

}
