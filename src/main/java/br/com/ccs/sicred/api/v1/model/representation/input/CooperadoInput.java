package br.com.ccs.sicred.api.v1.model.representation.input;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class CooperadoInput {

    @CPF
    private String cpf;
    private Boolean ativo = true;
}
