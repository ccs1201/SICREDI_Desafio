package br.com.ccs.sicred.core.utils.mapper;

import br.com.ccs.sicred.api.v1.model.model.input.CooperadoInput;
import br.com.ccs.sicred.api.v1.model.model.response.CooperadoResponse;
import br.com.ccs.sicred.domain.entity.Cooperado;
import org.springframework.stereotype.Component;

/**
 * Classe de Implmentação do {@code mapper} de un {@link Cooperado}
 */
@Component
public class CooperadoMapper extends AbstractMapper<CooperadoResponse, CooperadoInput, Cooperado> {
}
