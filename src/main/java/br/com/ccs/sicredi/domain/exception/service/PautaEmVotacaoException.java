package br.com.ccs.sicredi.domain.exception.service;

public class PautaEmVotacaoException extends BusinessLogicException {

    private static final String PAUTA_EM_VOTACAO_MESSAGE = "Pauta: %s, ja está em votação e não pode ser alterada.";

    public PautaEmVotacaoException(String tituloPauta) {
        super(String.format(PAUTA_EM_VOTACAO_MESSAGE, tituloPauta));
    }
}
