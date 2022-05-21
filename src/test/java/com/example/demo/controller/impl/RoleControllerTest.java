package com.example.demo.controller.impl;

import com.example.demo.DTO.RoleToUserDTO;
import com.example.demo.model.accounts.CheckingAccount;
import com.example.demo.model.secondary.Name;
import com.example.demo.model.security.Role;
import com.example.demo.model.users.ThirdParty;
import com.example.demo.model.users.User;
import com.example.demo.repository.security.RoleRepository;
import com.example.demo.repository.users.AccountHolderRepository;
import com.example.demo.repository.users.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
class RoleControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRepository userRepo;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<Role> roles;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        roles = roleRepo.saveAll(
                List.of(
                        new Role("ROLE_ADMIN"),
                        new Role("ROLE_ACCOUNTHOLDER")
                )
        );
        User user1 = userRepo.save(new User(new Name("Ingrid", "Bergman"), "ingridbergman", "1234"));
    }

    @AfterEach
    void tearDown() {
        roleRepo.deleteAll(roles);
    }

    @Test
    void getAllRoles() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("ADMIN"));
    }

    @Test
    void createRole() throws Exception {
        String body = objectMapper.writeValueAsString( new Role("ROLE_SUPERPOWER") );
        mockMvc.perform(post("/api/roles").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    void addRoleToUser() throws Exception {
        String body = objectMapper.writeValueAsString( new RoleToUserDTO("ingridbergman","ROLE_SUPERPOWER") );
        mockMvc.perform(patch("/api/roles/assign").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }
}