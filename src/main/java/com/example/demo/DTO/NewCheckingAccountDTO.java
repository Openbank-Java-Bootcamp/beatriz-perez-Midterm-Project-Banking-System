package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCheckingAccountDTO {

    private String secretKey;
    private Long primaryOwnerId;
    private BigDecimal balanceAmount;
    private String currencyCode;

}
