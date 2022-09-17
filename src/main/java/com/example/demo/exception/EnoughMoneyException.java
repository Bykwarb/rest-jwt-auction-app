package com.example.demo.exception;

public class EnoughMoneyException extends RuntimeException{
    public EnoughMoneyException(String message) {
        super(message);
    }
}
