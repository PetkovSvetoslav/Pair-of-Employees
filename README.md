# Pair-of-Employees-Task
1. Project Structure
   •	Maven: Use Maven for dependency management and project building.
   •	Spring Boot: Leverage Spring Boot for simplifying bootstrapping and development.
2. Model Classes
   •	Employee: Represents an employee with properties like id.
   •	Project: Represents a project with properties like id.
   •	EmployeeProject: Represents the association between an employee and a project. Properties include employeeId, projectId, dateFrom, and dateTo.
3. Data Parsing and Storage
   •	CSV Parser: A custom CSV parser to read the input file.
   •	Handle different date formats.
   •	Convert NULL in DateTo to the current date.
   •	Repository Layer: Use JPA repositories for CRUD operations (if persistence is implemented).
4. Core Functionalities
   •	Service Layer:
   •	EmployeeProjectService: Business logic to process data and find pairs of employees.
   •	EmployeeService & ProjectService: If implementing CRUD operations.
   •	Algorithm:
   •	Parse data and create a list of EmployeeProject instances.
   •	Calculate overlapping working days between employees on the same project.
   •	Aggregate total days worked together for each pair.
5. API Layer
   •	Controllers:
   •	EmployeeProjectController: Endpoints to upload CSV and get the longest working pair.
   •	EmployeeController & ProjectController: CRUD operations (if implemented).
6. Exception Handling
   •	Custom exceptions for file parsing errors, invalid data, etc.
7. Validation
   •	Validate CSV file format and data consistency.
8. Logging
   •	Implement logging for tracking application flow and errors.
9. Testing
   •	Unit tests for service layer methods.
   •	Integration tests for API endpoints.
