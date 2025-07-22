package com.example.validation.mapper;

import com.example.validation.dto.AddressResponseDTO;
import com.example.validation.dto.EmployeeRequestDTO;
import com.example.validation.dto.EmployeeResponseDTO;
import com.example.validation.model.Address;
import com.example.validation.model.Employee;

import java.util.List;

public class MapperUtil {
    public static EmployeeResponseDTO toDto(Employee employee) {
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setId(employee.getId());
        employeeResponseDTO.setFirstName(employee.getFirstName());
        employeeResponseDTO.setLastName(employee.getLastName());
        employeeResponseDTO.setDesignation(employee.getDesignation());
        employeeResponseDTO.setEmail(employee.getEmail());
        employeeResponseDTO.setPhone(employee.getPhone());

        List<AddressResponseDTO> addressResponseDTOS = employee.getAddress().stream()
                .map(address -> {
                    AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
                    addressResponseDTO.setStreet(address.getStreet());
                    addressResponseDTO.setCity(address.getCity());
                    addressResponseDTO.setState(address.getState());
                    addressResponseDTO.setZip(address.getZip());
                    return addressResponseDTO;
                }).toList();
        employeeResponseDTO.setAddresses(addressResponseDTOS);
        return employeeResponseDTO;
    }

    public static Employee toEntity(EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeRequestDTO.getFirstName());
        employee.setLastName(employeeRequestDTO.getLastName());
        employee.setDesignation(employeeRequestDTO.getDesignation());
        employee.setEmail(employeeRequestDTO.getEmail());
        employee.setPhone(employeeRequestDTO.getPhone());
        List<Address> addresses = employeeRequestDTO.getAddresses().stream()
                .map(addressRequestDTO -> {
                    Address address = new Address();
                    address.setStreet(addressRequestDTO.getStreet());
                    address.setCity(addressRequestDTO.getCity());
                    address.setState(addressRequestDTO.getState());
                    address.setZip(addressRequestDTO.getZip());
                    return address;
                }).toList();
        employee.setAddress(addresses);
        return employee;
    }
}
