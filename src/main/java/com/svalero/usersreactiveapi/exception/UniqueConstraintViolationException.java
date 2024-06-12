package com.svalero.usersreactiveapi.exception;

public class UniqueConstraintViolationException extends RuntimeException {
    public UniqueConstraintViolationException() {
        super();
    }

    public UniqueConstraintViolationException(String entity, String entity2, long entity2Id, String entity3, long entity3Id) {
        super(entity + " associated with " + entity2 + " " + entity2Id + " and " + entity3 + " " + entity3Id + " already exists");
    }

    public UniqueConstraintViolationException(String attribute, String value) { super(attribute + " " + value + " already in use"); }
}
