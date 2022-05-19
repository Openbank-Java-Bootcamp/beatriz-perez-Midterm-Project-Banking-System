package com.example.demo.DTO;

import com.example.demo.model.users.AccountHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewSavingsAccountDTO {

    private String secretKey;
    private Long primaryOwnerId;
    private java.math.BigDecimal balanceAmount;
    private String currencyCode;

}
