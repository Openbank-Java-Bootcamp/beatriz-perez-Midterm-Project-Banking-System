package com.example.demo.DTO;

import com.example.demo.model.users.AccountHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCreditCardAccountDTO {

    private String secretKey;
    private Long primaryOwnerId;
    private BigDecimal minimumBalanceAmount;
    private java.math.BigDecimal balanceAmount;
    private String currencyCode;

}
