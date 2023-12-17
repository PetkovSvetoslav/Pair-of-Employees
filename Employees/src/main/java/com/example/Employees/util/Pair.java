package com.example.Employees.util;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Data
@EqualsAndHashCode
public class Pair {
    private final Long employeeId1;
    private final Long employeeId2;

    public Pair(Long employeeId1, Long employeeId2) {
        this.employeeId1 = employeeId1;
        this.employeeId2 = employeeId2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return (employeeId1.equals(pair.employeeId1) && employeeId2.equals(pair.employeeId2)) ||
                (employeeId1.equals(pair.employeeId2) && employeeId2.equals(pair.employeeId1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId1, employeeId2) + Objects.hash(employeeId2, employeeId1);
    }

    @Override
    public String toString() {
        return "Pair{" + "employeeId1=" + employeeId1 + ", employeeId2=" + employeeId2 + '}';
    }
}

