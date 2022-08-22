package br.com.ccs.sicred.domain.exception.service;

public class EntityInUseException extends BusinessLogicException {

    private static final String INUSE_MESSAGE = "Não foi possível remover a entidade id: %d";

    public EntityInUseException(Long id) {
        super(String.format(INUSE_MESSAGE, id));
    }

    public EntityInUseException(Long id, Throwable cause) {
        super(String.format(INUSE_MESSAGE, id), cause);
    }

    public EntityInUseException(Throwable cause) {
        super(cause);
    }
}
