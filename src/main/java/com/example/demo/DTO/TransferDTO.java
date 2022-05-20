package com.example.demo.DTO;

import com.example.demo.model.secondary.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {

    private Long originAccountNumber;
    private BigDecimal amount;
    private String receiverName;
    private Long destinationAccountNumber;

}
