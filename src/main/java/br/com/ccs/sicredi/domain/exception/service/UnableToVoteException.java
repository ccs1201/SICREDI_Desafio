package br.com.ccs.sicredi.domain.exception.service;

public class UnableToVoteException extends BusinessLogicException {
    public UnableToVoteException(String message) {
        super(message);
    }
}
