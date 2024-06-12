package com.svalero.usersreactiveapi.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Authentication Failed");
    }
}
