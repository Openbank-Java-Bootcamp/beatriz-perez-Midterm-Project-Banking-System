package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleToUserDTO {

    private Long userId;

    private String roleName;

}
