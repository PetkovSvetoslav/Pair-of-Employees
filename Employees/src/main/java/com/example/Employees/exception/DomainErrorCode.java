package com.example.Employees.exception;

public enum DomainErrorCode {

    GENERAL_ERROR(5000, "General error"),
    DATA_IS_NULL(5001, "Data is required."),
    INVALID_DATA(5002,"Invalid data format in CSV"),
    WRONG_DATA(5003,"File format is not CSV"),
    ERROR_CSV(5004,"Error parsing the CSV file"),
    INVALID_CSV(5005,"Invalid number format in CSV");

    private final int code;
    private final String message;

    DomainErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
