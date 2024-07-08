package org.example.backend.store.except;

// StoreNotFoundException 예외 클래스
public class StoreNotFoundException extends RuntimeException {
    public StoreNotFoundException(String message) {
        super(message);
    }
}

