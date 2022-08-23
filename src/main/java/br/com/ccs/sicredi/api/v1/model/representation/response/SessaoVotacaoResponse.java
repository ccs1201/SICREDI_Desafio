package br.com.ccs.sicredi.api.v1.model.representation.response;

import br.com.ccs.sicredi.domain.entity.Pauta;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessaoVotacaoResponse {

    private Long id;
    private Boolean Aprovada;
    private OffsetDateTime dataAbertura;
    private OffsetDateTime dataEncerramento;
    private long duracaoEmMinutosAposAbertura;
    private Long totalVotosSim = 0L;
    private Long totalVotosNao = 0L;
    private Pauta pauta;
}
