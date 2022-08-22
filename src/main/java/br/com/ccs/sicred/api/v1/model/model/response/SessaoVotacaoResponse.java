package br.com.ccs.sicred.api.v1.model.model.response;

import br.com.ccs.sicred.domain.entity.Pauta;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessaoVotacaoResponse {

    private Long id;
    private boolean abertaParaVoto;
    private OffsetDateTime dataAbertura;
    private OffsetDateTime dataEncerramento;
    private long duracaoEmMinutosAposAbertura;
    private Pauta pauta;
}
