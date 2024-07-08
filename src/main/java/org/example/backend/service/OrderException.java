package org.example.backend.service;

public class OrderException extends RuntimeException {
    public OrderException(String message) {
        super(message);
    }
}
