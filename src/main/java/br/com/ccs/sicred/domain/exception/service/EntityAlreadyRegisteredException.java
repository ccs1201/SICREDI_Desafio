package br.com.ccs.sicred.domain.exception.service;

public class EntityAlreadyRegisteredException extends BusinessLogicException {
    public EntityAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAlreadyRegisteredException(String message) {
        super(message);
    }
}
