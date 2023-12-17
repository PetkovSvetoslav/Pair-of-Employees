package com.example.Employees.exception;

public class DomainException extends Exception {

    public DomainException(DomainErrorCode errorCode) {
        super(String.valueOf(errorCode));
    }

    public DomainException(DomainErrorCode errorCode, String customMessage) {
        super();
    }

    public DomainException(DomainErrorCode errorCode, Throwable e) {
        super(String.valueOf(errorCode), e);
    }

    public DomainException(DomainErrorCode errorCode, String customMessage, Throwable e) {
        super();
    }
}