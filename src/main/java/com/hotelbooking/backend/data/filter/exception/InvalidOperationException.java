package com.hotelbooking.backend.data.filter.exception;

public class InvalidOperationException extends Exception{
    public InvalidOperationException() {
        super("Operation was invalid");
    }
}
