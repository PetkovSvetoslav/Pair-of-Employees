package com.example.Employees.service;

import com.example.Employees.exception.DomainErrorCode;
import com.example.Employees.model.entity.EmployeeProject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVParserService {

    public List<EmployeeProject> parseCSVFile(MultipartFile file) throws DomainException {
        if (!isCSVFile(file)) {
            throw new DomainException(DomainErrorCode.WRONG_DATA);
        }

        List<EmployeeProject> employeeProjects = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length != 4) {
                    throw new DomainException(DomainErrorCode.INVALID_DATA);
                }
                employeeProjects.add(createEmployeeProject(data));
            }
        } catch (Exception e) {
            throw new DomainException(DomainErrorCode.ERROR_CSV);
        }
        return employeeProjects;
    }

    private boolean isCSVFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.equals("text/csv");
    }

    private EmployeeProject createEmployeeProject(String[] data) throws DomainException {
        EmployeeProject employeeProject = new EmployeeProject();
        try {
            employeeProject.setEmployeeId(Long.parseLong(data[0].trim()));
            employeeProject.setProjectId(Long.parseLong(data[1].trim()));
            employeeProject.setDateFrom(parseDate(data[2].trim()));
            employeeProject.setDateTo(parseDate(data[3].trim()));
        } catch (NumberFormatException e) {
            throw new DomainException(DomainErrorCode.INVALID_CSV);
        }
        return employeeProject;
    }

    private LocalDate parseDate(String dateString) throws DomainException {
        if (dateString.equalsIgnoreCase("NULL")) {
            return LocalDate.now();
        }
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new DomainException(DomainErrorCode.INVALID_DATA);
        }
    }
}

class DomainException extends Exception {
    public DomainException(DomainErrorCode message) {
        super(String.valueOf(message));
    }
}
