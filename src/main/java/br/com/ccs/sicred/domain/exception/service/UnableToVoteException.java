package br.com.ccs.sicred.domain.exception.service;

public class UnableToVoteException extends BusinessLogicException {
    public UnableToVoteException(String message) {
        super(message);
    }
}
