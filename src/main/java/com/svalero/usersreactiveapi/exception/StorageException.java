package com.svalero.usersreactiveapi.exception;

public class StorageException extends RuntimeException {
    public StorageException() {
        super("Storage Exception");
    }

    public StorageException(Throwable cause) {
        super("Storage Exception", cause);
    }
}
