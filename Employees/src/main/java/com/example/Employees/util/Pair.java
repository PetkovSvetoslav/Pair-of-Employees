package com.example.Employees.util;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Pair {
    private final Long employeeId1;
    private final Long employeeId2;

    public Pair(Long employeeId1, Long employeeId2) {
        this.employeeId1 = employeeId1;
        this.employeeId2 = employeeId2;
    }
    @Override
    public String toString() {
        return "Pair{" + "employeeId1=" + employeeId1 + ", employeeId2=" + employeeId2 + '}';
    }
}
