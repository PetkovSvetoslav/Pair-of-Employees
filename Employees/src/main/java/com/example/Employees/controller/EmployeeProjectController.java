package com.example.Employees.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.Employees.exception.DomainException;
import com.example.Employees.model.entity.EmployeeProject;
import com.example.Employees.repository.EmployeeProjectRepository;
import com.example.Employees.service.CSVParserService;
import com.example.Employees.service.EmployeeProjectService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class EmployeeProjectController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeProjectController.class);

    private final EmployeeProjectService employeeProjectService;
    private final CSVParserService csvParserService;
    private final EmployeeProjectRepository employeeProjectRepository;

    public EmployeeProjectController(EmployeeProjectService employeeProjectService,
                                     CSVParserService csvParserService,
                                     EmployeeProjectRepository employeeProjectRepository) {
        this.employeeProjectService = employeeProjectService;
        this.csvParserService = csvParserService;
        this.employeeProjectRepository = employeeProjectRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            List<EmployeeProject> employeeProjects = csvParserService.parseCSVFile(file);
            employeeProjectRepository.saveAll(employeeProjects);
            List<String> pairs = employeeProjectService.findLongestWorkingPairs();
            model.addAttribute("pairs", pairs);
        } catch (DomainException e) {
            logger.error("Error processing CSV file", e);
            model.addAttribute("errorMessage", "Error processing file: " + e.getMessage());
        }
        return "index";
    }
}
