package br.com.ccs.sicredi.domain.exception.service;

public class EntityPersistException extends BusinessLogicException {

    private static final String PERSIST_MESSAGE = "Não foi possível Salvar os dados do(a) %s";

    public EntityPersistException(String nomeEntidade, Throwable cause) {
        super(String.format(PERSIST_MESSAGE, nomeEntidade), cause);
    }

    public EntityPersistException(String nomeEntidade) {
        super(String.format(PERSIST_MESSAGE, nomeEntidade));
    }
}
