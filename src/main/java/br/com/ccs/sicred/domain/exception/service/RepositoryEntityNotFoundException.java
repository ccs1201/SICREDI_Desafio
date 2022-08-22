package br.com.ccs.sicred.domain.exception.service;

public class RepositoryEntityNotFoundException extends BusinessLogicException {
    public RepositoryEntityNotFoundException(String message) {

        super(message);
    }

    public RepositoryEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryEntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
