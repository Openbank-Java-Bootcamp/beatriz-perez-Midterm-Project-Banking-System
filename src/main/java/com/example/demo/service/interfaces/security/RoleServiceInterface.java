package com.example.demo.service.interfaces.security;

import com.example.demo.model.security.Role;

public interface RoleServiceInterface {

    Role saveRole(Role role);

    void addRoleToUser(Long userId, String roleName);

}
