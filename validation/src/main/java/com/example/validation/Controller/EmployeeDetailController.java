package com.example.validation.Controller;

import com.example.validation.dto.EmployeeRequestDTO;
import com.example.validation.dto.EmployeeResponseDTO;
import com.example.validation.service.EmployeeDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/employee_detail")
@RequiredArgsConstructor
public class EmployeeDetailController {
    private final EmployeeDetailService employeeDetailService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployeeDetail(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return ResponseEntity.ok().body(employeeDetailService.saveEmployeeDetail(employeeRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployeeDetails() {
        return ResponseEntity.ok().body(employeeDetailService.getAllEmployeeDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeDetailById(@PathVariable("id") UUID employeeId) {
        return ResponseEntity.ok().body(employeeDetailService.getEmployeeDetailById(employeeId));
    }

}
