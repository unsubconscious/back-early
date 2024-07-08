package org.example.backend.store.except;

// StoreServiceException 예외 클래스
public class StoreServiceException extends RuntimeException {
    public StoreServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
