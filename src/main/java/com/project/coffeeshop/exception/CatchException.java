package com.project.coffeeshop.exception;

public class CatchException extends RuntimeException{

    public CatchException(String message) {
        super(message);
    }

    public CatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
