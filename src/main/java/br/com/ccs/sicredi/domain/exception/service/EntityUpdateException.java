package br.com.ccs.sicredi.domain.exception.service;

public class EntityUpdateException extends BusinessLogicException {

    private static final String UPDATE_MESSAGE = "Não foi salvar o(a) %s";

    public EntityUpdateException(String NomeEntidade) {
        super(String.format(UPDATE_MESSAGE, NomeEntidade));
    }

    public EntityUpdateException(String NomeEntidade, Throwable cause) {
        super(String.format(UPDATE_MESSAGE, NomeEntidade), cause);
    }

    public EntityUpdateException(Throwable cause) {
        super(cause);
    }
}
