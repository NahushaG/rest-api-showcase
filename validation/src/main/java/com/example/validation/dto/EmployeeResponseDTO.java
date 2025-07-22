package com.example.validation.dto;

import com.example.validation.model.Designation;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmployeeResponseDTO {
    private long id;
    private String firstName;
    private String lastName;
    private Designation designation;
    private String email;
    private String phone;
    List<AddressResponseDTO> addresses;
}



