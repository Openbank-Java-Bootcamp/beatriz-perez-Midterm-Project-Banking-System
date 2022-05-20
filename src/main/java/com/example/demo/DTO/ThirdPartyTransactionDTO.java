package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyTransactionDTO {

    // User info:
    private String username;
    // key in the header of the HTTP request <------------------------
    private String password;

    // Transaction info:
    private BigDecimal amount; // Amount will be added (negative for charging, positive for sending money)
    private String currencyCode;
    private Long accountNumber;
    private String accountSecretKey;

}
