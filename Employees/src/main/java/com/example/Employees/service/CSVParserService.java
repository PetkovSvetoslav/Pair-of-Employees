package com.example.Employees.service;


import com.example.Employees.controller.EmployeeProjectController;
import com.example.Employees.exception.DomainErrorCode;
import com.example.Employees.exception.DomainException;
import com.example.Employees.model.entity.EmployeeProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CSVParserService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeProjectController.class);

    public List<EmployeeProject> parseCSVFile(MultipartFile file) throws DomainException {
        if (!isCSVFile(file)) {
            throw new DomainException(DomainErrorCode.WRONG_DATA.toString());
        }

        List<EmployeeProject> employeeProjects = new ArrayList<>();
        String line = null;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                logger.debug("Parsing line: " + line);

                String[] data = line.split(",");
                logger.debug("Data array: " + Arrays.toString(data));
                if (data.length != 4) {
                    logger.error("Incorrect number of fields in line: " + line);
                    throw new DomainException(DomainErrorCode.INVALID_DATA.toString());
                }
                employeeProjects.add(createEmployeeProject(data));
            }
        } catch (Exception e) {
            logger.error("Error parsing CSV file at line: " + line + " - Error: " + e.getMessage(), e);
            throw new DomainException("ERROR_CSV: " + e.getMessage());
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
            employeeProject.setEmployeeId(parseLong(data[0].trim(), "EmpID"));
            employeeProject.setProjectId(parseLong(data[1].trim(), "ProjectID"));
            employeeProject.setDateFrom(parseDate(data[2].trim()));
            employeeProject.setDateTo(data.length < 4 || data[3].trim().isEmpty() ? LocalDate.now() : parseDate(data[3].trim()));
        } catch (NumberFormatException e) {
            throw new DomainException("INVALID_CSV: Error parsing " + e.getMessage());
        }
        return employeeProject;
    }


    private Long parseLong(String value, String fieldName) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(fieldName + " value '" + value + "' is not a valid number.");
        }
    }

//    private LocalDate parseDate(String dateString) throws DomainException {
//        if (dateString.equalsIgnoreCase("NULL")) {
//            return LocalDate.now();
//        }
//        try {
//            return tryParseDate(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
//        } catch (DateTimeParseException e) {
//            try {
//                return tryParseDate(dateString, DateTimeFormatter.ofPattern("M/d/yyyy"));
//            } catch (DateTimeParseException ex) {
//                logger.error("Invalid date format in line: '" + dateString + "'");
//                throw new DomainException("INVALID_DATA: Date '" + dateString + "' is not in a valid format.");
//            }
//        }
//    }
private LocalDate parseDate(String dateString) throws DomainException {
    dateString = dateString != null ? dateString.trim() : "";

    if (dateString.isEmpty() || dateString.equalsIgnoreCase("NULL")) {
        return LocalDate.now();
    }

    List<DateTimeFormatter> formatters = List.of(
            DateTimeFormatter.ISO_LOCAL_DATE,
            DateTimeFormatter.ofPattern("M/d/yyyy")
    );

    for (DateTimeFormatter formatter : formatters) {
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            logger.debug("Failed to parse date '" + dateString + "' with format: " + formatter);
        }
    }

    logger.error("Invalid date format: '" + dateString + "'");
    throw new DomainException("INVALID_DATA: Date '" + dateString + "' is not in a valid format.");
}


    private LocalDate tryParseDate(String dateString, DateTimeFormatter formatter) throws DateTimeParseException {
        return LocalDate.parse(dateString, formatter);
    }
}
