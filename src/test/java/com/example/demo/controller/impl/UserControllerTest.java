package com.example.demo.controller.impl;

import com.example.demo.model.secondary.Address;
import com.example.demo.model.secondary.Name;
import com.example.demo.model.security.Role;
import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.users.ThirdParty;
import com.example.demo.model.users.User;
import com.example.demo.repository.security.RoleRepository;
import com.example.demo.repository.users.AccountHolderRepository;
import com.example.demo.repository.users.ThirdPartyRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AccountHolderRepository accountHolderRepo;
    @Autowired
    private ThirdPartyRepository thirdPartyRepo;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<Role> roles;
    private List<User> users;
    private List<ThirdParty> thirdParties;
    private List<AccountHolder> accountHolders;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        roles = roleRepo.saveAll(
                List.of(
                        new Role("ROLE_ADMIN"),
                        new Role("ROLE_ACCOUNTHOLDER")
                )
        );
        users = userRepo.saveAll(
                List.of(
                        new User(new Name("Rita", "Hayworth"), "ritahayworth", "gilda" ),
                        new User(new Name("James", "Dean"), "jamesdean", "eastofeden" )
                )
        );
        thirdParties = thirdPartyRepo.saveAll(
                List.of(
                        new ThirdParty("clarkgable", "butler"),
                        new ThirdParty("vivienleigh", "ohara")
                )
        );
        accountHolders = accountHolderRepo.saveAll(
                List.of(
                        new AccountHolder(
                                new Name("Scarlett", "Ohara"),
                                "scarlettohara",
                                "wind",
                                "1913-11-05",
                                new Address("Tara", "Georgia", "USA", "54321")
                        )
                )
        );
    }

    @AfterEach
    void tearDown() {
        roleRepo.deleteAll(roles);
        userRepo.deleteAll(users);
        thirdPartyRepo.deleteAll(thirdParties);
        accountHolderRepo.deleteAll(accountHolders);
    }

    @Test
    void getAllUsers() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Hayworth"));
        assertTrue(result.getResponse().getContentAsString().contains("Dean"));
        assertTrue(result.getResponse().getContentAsString().contains("Ohara"));
    }

    @Test
    void getUserById() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Hayworth"));
    }

    @Test
    void getAllThirdParties() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users/third-party"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("gable"));
        assertTrue(result.getResponse().getContentAsString().contains("leigh"));
    }

    @Test
    void createUser() throws Exception {
        String body = objectMapper.writeValueAsString( new User(new Name("Marlon", "Brando"), "marlonbrando", "godfather") );
        mockMvc.perform(post("/api/users/admin-user").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    void createAccountHolder() throws Exception {
        String body = objectMapper.writeValueAsString(
                new AccountHolder(
                        new Name("Marilyn", "Monroe"),
                        "marilynmonroe",
                        "niagara",
                        "1926-06-01",
                        new Address("BrentWood", "LA", "USA", "01234")
                )
        );
        mockMvc.perform(post("/api/users/account-holder").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    void createThirdParty() throws Exception {
        String body = objectMapper.writeValueAsString(
                new ThirdParty(
                        "marilynmonroe",
                        "niagara"
                )
        );
        mockMvc.perform(post("/api/users/third-party").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    void updateUserById() throws Exception {
        String body = objectMapper.writeValueAsString( new User(new Name("Ms Rita", "Hayworth"), "ritahayworth", "gilda" ) );
        mockMvc.perform(put("/api/users/admin-user/1").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
        assertEquals(userRepo.findById(1L).get().getName().getFirstName(), "Ms Rita");
    }

    @Test
    void updateAccountHolderById() throws Exception {
        String body = objectMapper.writeValueAsString(
                new AccountHolder(
                        new Name("Ms Scarlett", "Ohara"),
                        "scarlettohara",
                        "wind",
                        "1913-11-05",
                        new Address("Tara", "Georgia", "USA", "54321")
                )
        );
        mockMvc.perform(put("/api/users/account-holder/1").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
        assertEquals(accountHolderRepo.findById(1L).get().getName().getFirstName(), "Ms Scarlett");
    }

    @Test
    void updateThirdPartyById() throws Exception {
        String body = objectMapper.writeValueAsString( new ThirdParty( "rhettbutler", "butler" ) );
        mockMvc.perform(put("/api/users/third-party/1").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
        assertEquals(thirdPartyRepo.findById(1L).get().getUsername(), "rhettbutler");
    }

    @Test
    void deleteUserById() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteThirdPartyById() throws Exception {
        mockMvc.perform(delete("/api/users/third-party/1"))
                .andExpect(status().isNoContent());
    }
}