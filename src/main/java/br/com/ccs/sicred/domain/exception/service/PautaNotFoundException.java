package br.com.ccs.sicred.domain.exception.service;

public class PautaNotFoundException extends BusinessLogicException {

    private static final String MESSAGE_ID = "Pauta id: %d, não localizada.";
    private static final String MESSAGE_TITULO = "Nenhuma Pauta com titulo: %s, localizada.";

    public PautaNotFoundException(Long id) {
        super(String.format(MESSAGE_ID, id));
    }

    public PautaNotFoundException() {
        super("Nenhuma pauta localizada.");
    }

    public PautaNotFoundException(String titulo) {
        super(String.format(MESSAGE_TITULO, titulo));
    }
}
