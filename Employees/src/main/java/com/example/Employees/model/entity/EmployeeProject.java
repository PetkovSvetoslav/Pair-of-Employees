package com.example.Employees.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Entity
@IdClass(EmployeeProjectId.class)
public class EmployeeProject {

    @Id
    private Long employeeId;

    @Id
    private Long projectId;

    private LocalDate dateFrom;
    private LocalDate dateTo;

}
