package com.example.Employees.repository;

import com.example.Employees.model.entity.EmployeeProject;
import com.example.Employees.model.entity.EmployeeProjectId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, EmployeeProjectId> {
}
