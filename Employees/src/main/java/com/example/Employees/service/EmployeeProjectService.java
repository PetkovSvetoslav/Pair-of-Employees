package com.example.Employees.service;

import com.example.Employees.model.entity.EmployeeProject;
import com.example.Employees.repository.EmployeeProjectRepository;
import com.example.Employees.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class EmployeeProjectService {

    private final EmployeeProjectRepository employeeProjectRepository;

    public EmployeeProjectService(EmployeeProjectRepository employeeProjectRepository) {
        this.employeeProjectRepository = employeeProjectRepository;
    }

 public List<String> findLongestWorkingPairs() {
    List<EmployeeProject> allProjects = employeeProjectRepository.findAll();
    Map<Pair, Map<Long, Integer>> pairProjectDays = new HashMap<>();

    for (EmployeeProject project1 : allProjects) {
        for (EmployeeProject project2 : allProjects) {
            if (!project1.getProjectId().equals(project2.getProjectId()) || project1.getEmployeeId().equals(project2.getEmployeeId())) {
                continue;
            }

            Long empId1 = Math.min(project1.getEmployeeId(), project2.getEmployeeId());
            Long empId2 = Math.max(project1.getEmployeeId(), project2.getEmployeeId());

            Pair pair = new Pair(empId1, empId2);
            int daysWorkedTogether = calculateOverlapDays(project1, project2);

            pairProjectDays.computeIfAbsent(pair, k -> new HashMap<>())
                    .merge(project1.getProjectId(), daysWorkedTogether, Integer::sum);
        }
    }

    return formatOutput(pairProjectDays);
}


    private int calculateOverlapDays(EmployeeProject project1, EmployeeProject project2) {
        LocalDate start = max(project1.getDateFrom(), project2.getDateFrom());
        LocalDate end = min(project1.getDateTo(), project2.getDateTo());
        return (int) (start.isBefore(end) ? ChronoUnit.DAYS.between(start, end) + 1 : 0);
    }

    private LocalDate max(LocalDate d1, LocalDate d2) {
        return d1.isAfter(d2) ? d1 : d2;
    }

    private LocalDate min(LocalDate d1, LocalDate d2) {
        return d1.isBefore(d2) ? d1 : (d2 != null ? d2 : d1);
    }

private List<String> formatOutput(Map<Pair, Map<Long, Integer>> pairProjectDays) {
    List<String> output = new ArrayList<>();

    pairProjectDays.forEach((pair, projectDays) -> {
        int totalDays = projectDays.values().stream().mapToInt(Integer::intValue).sum();

        if (totalDays > 0) {
            output.add(pair.getEmployeeId1() + ", " + pair.getEmployeeId2() + ", " + totalDays);

            projectDays.forEach((projectId, days) -> {
                if (days > 0) {
                    output.add("\t" + projectId + ", " + days);
                }
            });
        }
    });

    return output;
}


}

