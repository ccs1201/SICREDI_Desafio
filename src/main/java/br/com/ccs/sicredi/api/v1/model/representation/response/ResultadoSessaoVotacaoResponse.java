package br.com.ccs.sicredi.api.v1.model.representation.response;

import br.com.ccs.sicredi.domain.entity.Pauta;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultadoSessaoVotacaoResponse {

    private Long id;

    private Pauta pauta;

    private Long totalVotosSim;

    private Long totalVotosNao;

    private Boolean Aprovada;

    private Boolean encerrada;

    private OffsetDateTime dataAbertura;

    private OffsetDateTime dataEncerramento;
}
