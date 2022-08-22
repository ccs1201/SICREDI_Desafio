package br.com.ccs.sicred.api.v1.model.representation.input;

import br.com.ccs.sicred.api.v1.model.representation.PautaIdInput;
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
