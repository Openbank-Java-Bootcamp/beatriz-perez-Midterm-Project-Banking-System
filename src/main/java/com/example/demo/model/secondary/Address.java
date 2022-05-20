package com.example.demo.model.secondary;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {

    private String streetAddress;
    private String city;
    private String country;
    private String postalCode;

}
