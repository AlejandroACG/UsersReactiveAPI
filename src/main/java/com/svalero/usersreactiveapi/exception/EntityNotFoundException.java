package com.svalero.usersreactiveapi.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entity, long id) {
        super(entity + " " + id + " not found");
    }
}
