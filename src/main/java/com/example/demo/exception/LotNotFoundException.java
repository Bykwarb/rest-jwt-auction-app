package com.example.demo.exception;

public class LotNotFoundException extends RuntimeException{
    public LotNotFoundException(String message) {
        super(message);
    }
}
