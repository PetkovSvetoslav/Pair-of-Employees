package com.example.Employees.service;

import com.example.Employees.repository.EmployeeProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeProjectService {

    private final EmployeeProjectRepository employeeProjectRepository;

    public EmployeeProjectService(EmployeeProjectRepository employeeProjectRepository) {
        this.employeeProjectRepository = employeeProjectRepository;
    }


}
