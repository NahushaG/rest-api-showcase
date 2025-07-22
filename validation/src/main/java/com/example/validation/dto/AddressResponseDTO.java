package com.example.validation.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AddressResponseDTO {
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
}
