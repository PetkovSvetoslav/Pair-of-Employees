package com.example.Employees.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@EqualsAndHashCode
public class EmployeeProjectId implements Serializable {
    private Long employeeId;
    private Long projectId;
}
