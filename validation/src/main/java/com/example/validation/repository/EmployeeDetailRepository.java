package com.example.validation.repository;

import com.example.validation.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeDetailRepository extends JpaRepository<Employee, UUID> {

}
