package com.example.demo.controller.impl;

import com.example.demo.DTO.AccountBalanceOnlyDTO;
import com.example.demo.DTO.ThirdPartyTransactionDTO;
import com.example.demo.DTO.TransferDTO;
import com.example.demo.model.accounts.Account;
import com.example.demo.model.accounts.CheckingAccount;
import com.example.demo.model.accounts.CreditCardAccount;
import com.example.demo.model.accounts.SavingsAccount;
import com.example.demo.model.secondary.Address;
import com.example.demo.model.secondary.Name;
import com.example.demo.model.users.AccountHolder;
import com.example.demo.model.users.ThirdParty;
import com.example.demo.repository.accounts.AccountRepository;
import com.example.demo.repository.users.AccountHolderRepository;
import com.example.demo.repository.users.ThirdPartyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
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
class AccountControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private AccountHolderRepository accountHolderRepo;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private ThirdPartyRepository thirdPartyRepo;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private AccountHolder owner1;
    private List<Account> accounts;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        thirdPartyRepo.save(new ThirdParty("clarkgable", "butler"));
        owner1 = new AccountHolder(
                new Name("Ms Scarlett", "Ohara"),
                "scarlettohara",
                "wind",
                "1913-11-05",
                new Address("Tara", "Georgia", "USA", "54321")
        );
        accountHolderRepo.save(owner1);
        accounts = accountRepo.saveAll(
                List.of(
                        new SavingsAccount("3210", owner1, null, new BigDecimal("10000"), Currency.getInstance("EUR")),
                        new CreditCardAccount("abcd", owner1, null, new BigDecimal("1000"), Currency.getInstance("EUR")),
                        new CheckingAccount("1234", owner1, null, new BigDecimal("250"), Currency.getInstance("EUR"))
                )
        );
    }

    @AfterEach
    void tearDown() {
        accountHolderRepo.delete(owner1);
        accountRepo.deleteAll(accounts);
    }

    @Test
    void getAllAccounts() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Ohara"));
    }

    @Test
    void getAllAccountsByOwner() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/accounts/user/1"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Ohara"));
    }

    @Test
    void getAccountByNumber() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Ohara"));
    }

    @Test
    void createCheckingAccount() throws Exception {
        String body = objectMapper.writeValueAsString(new CheckingAccount("1234", owner1, null, new BigDecimal("250"), Currency.getInstance("EUR")));
        mockMvc.perform(post("/api/accounts/checking").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    void createCreditCardAccount() throws Exception {
        String body = objectMapper.writeValueAsString( new CreditCardAccount("abcd", owner1, null, new BigDecimal("1000"), Currency.getInstance("EUR")) );
        mockMvc.perform(post("/api/accounts/credit-card").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    void createSavingsAccount() throws Exception {
        String body = objectMapper.writeValueAsString( new SavingsAccount("3210", owner1, null, new BigDecimal("10000"), Currency.getInstance("EUR")) );
        mockMvc.perform(post("/api/accounts/savings").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    void updateAccountBalance() throws Exception {
        String body = objectMapper.writeValueAsString(new AccountBalanceOnlyDTO(new BigDecimal("20000")));
        mockMvc.perform(patch("/api/accounts/1").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }

    @Test
    void operateAsThirdParty() throws Exception {
        String body = objectMapper.writeValueAsString(
                new ThirdPartyTransactionDTO("clarkgable", "butler", new BigDecimal("100"), "EUR", 1L, "3210" )
        );
        mockMvc.perform(patch("/api/accounts/third-party-transaction").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }

    @Test
    void transferMoney() throws Exception {
        String body = objectMapper.writeValueAsString(
                new TransferDTO(1L, new BigDecimal("100"), "Ms Scarlett Ohara", 2L)
        );
        mockMvc.perform(patch("/api/account-holder/transfer").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }

    @Test
    void deleteAccountByNumber() throws Exception {
        mockMvc.perform(delete("/api/accounts/1")).andExpect(status().isNoContent());
    }
}