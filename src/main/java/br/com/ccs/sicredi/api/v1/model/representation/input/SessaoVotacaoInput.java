package br.com.ccs.sicredi.api.v1.model.representation.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class SessaoVotacaoInput {
    @Valid
    @NotNull
    private PautaIdInput pauta;

    //Duração de uma sessão em minutos
    @NotNull
    @Positive
    private Long duracaoEmMinutosAposAbertura = 1L;
}
