package br.com.ccs.sicred.api.v1.model.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * <p><b>Representa uma Response de {@link br.com.ccs.sicred.domain.entity.Cooperado}</b></p>
 */

@Getter
@Setter
public class CooperadoResponse {
    private Long id;
    private String cpf;
    private Boolean ativo;

}
