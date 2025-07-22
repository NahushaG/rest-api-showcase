package com.example.validation.service;

import com.example.validation.dto.EmployeeRequestDTO;
import com.example.validation.dto.EmployeeResponseDTO;
import com.example.validation.exception.NoEmployeeFoundException;
import com.example.validation.mapper.MapperUtil;
import com.example.validation.model.Employee;
import com.example.validation.repository.EmployeeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeDetailService {
    private final EmployeeDetailRepository employeeDetailRepository;

    public EmployeeResponseDTO saveEmployeeDetail(EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = MapperUtil.toEntity(employeeRequestDTO);
        Employee savedEmployee = employeeDetailRepository.save(employee);
        return MapperUtil.toDto(savedEmployee);
    }

    public EmployeeResponseDTO getEmployeeDetailById(UUID employeeId) {
        Employee employee = employeeDetailRepository.findById(employeeId)
                .orElseThrow(() -> new NoEmployeeFoundException("No employee found for employee id" + employeeId));
        return MapperUtil.toDto(employee);
    }

    public List<EmployeeResponseDTO> getAllEmployeeDetails() {
        List<Employee> employeeList = employeeDetailRepository.findAll();
        return employeeList.stream().map(MapperUtil::toDto).collect(Collectors.toList());
    }
}
