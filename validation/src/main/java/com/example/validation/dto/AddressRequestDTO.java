package com.example.validation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AddressRequestDTO {
    @NotBlank(message = "Address should not be blank")
    private String street;
    @NotBlank(message = "Address should not be blank")
    private String city;
    private String state;
    @Pattern(regexp = "^\\d{5}$", message = "Invalid postal code")
    private String zip;
    @NotBlank(message = "Address should not be blank")
    private String country;
}
