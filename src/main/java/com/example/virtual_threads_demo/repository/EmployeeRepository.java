package com.example.virtual_threads_demo.repository;

import com.example.virtual_threads_demo.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Additional query methods can be defined here if needed
}
