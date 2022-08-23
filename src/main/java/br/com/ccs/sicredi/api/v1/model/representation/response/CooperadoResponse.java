package br.com.ccs.sicredi.api.v1.model.representation.response;

import br.com.ccs.sicredi.domain.entity.Cooperado;
import lombok.Getter;
import lombok.Setter;

/**
 * <p><b>Representa um Response Model de {@link Cooperado}</b></p>
 */

@Getter
@Setter
public class CooperadoResponse {
    private Long id;
    private String cpf;
    private Boolean ativo;

}
