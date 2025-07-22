package com.example.validation.dto;

import com.example.validation.model.Designation;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmployeeRequestDTO {
    @NotBlank(message = "FirstName is mandatory")
    private String firstName;
    private String lastName;
    @NotNull(message = "Designation is mandatory")
    private Designation designation;
    @Email(message = "Invalid email id")
    private String email;
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid phone number")
    private String phone;
    @Size(min = 1, message = "Need at least one address")
    List<AddressRequestDTO> addresses;

}
