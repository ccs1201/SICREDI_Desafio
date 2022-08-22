package br.com.ccs.sicred.api.v1.model.representation.response;

import br.com.ccs.sicred.domain.entity.Pauta;

import java.time.OffsetDateTime;

public class ResultadoSessaoResponse {

    private Long id;

    private Pauta pauta;

    private Long totalVotosSim;

    private Long totalVotosNao;

    private Boolean Aprovada;

    private Boolean encerrada;

    private OffsetDateTime dataAbertura;

    private OffsetDateTime dataEncerramento;
}
