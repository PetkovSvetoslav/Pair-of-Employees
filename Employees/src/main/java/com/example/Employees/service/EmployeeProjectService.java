package com.example.Employees.service;

import com.example.Employees.model.entity.EmployeeProject;
import com.example.Employees.repository.EmployeeProjectRepository;
import com.example.Employees.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeProjectService {

    private final EmployeeProjectRepository employeeProjectRepository;

    public EmployeeProjectService(EmployeeProjectRepository employeeProjectRepository) {
        this.employeeProjectRepository = employeeProjectRepository;
    }

    public List<String> findLongestWorkingPairs() {
        List<EmployeeProject> allProjects = employeeProjectRepository.findAll();
        Map<Pair, Integer> pairWorkingDays = new HashMap<>();

        for (int i = 0; i < allProjects.size(); i++) {
            for (int j = i + 1; j < allProjects.size(); j++) {
                EmployeeProject project1 = allProjects.get(i);
                EmployeeProject project2 = allProjects.get(j);

                if (project1.getProjectId().equals(project2.getProjectId())) {
                    Pair pair = new Pair(project1.getEmployeeId(), project2.getEmployeeId());
                    int daysWorkedTogether = calculateOverlapDays(project1, project2);
                    pairWorkingDays.merge(pair, daysWorkedTogether, Integer::sum);
                }
            }
        }

        return formatOutput(pairWorkingDays);
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

    private List<String> formatOutput(Map<Pair, Integer> pairWorkingDays) {
        List<String> output = new ArrayList<>();
        pairWorkingDays.forEach((pair, days) -> output.add(pair.toString() + ", " + days));
        return output;
    }
}
