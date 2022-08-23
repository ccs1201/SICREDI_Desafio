package br.com.ccs.sicredi.core.utils.mapper;

import br.com.ccs.sicredi.api.v1.model.representation.input.CooperadoInput;
import br.com.ccs.sicredi.api.v1.model.representation.response.CooperadoResponse;
import br.com.ccs.sicredi.domain.entity.Cooperado;
import org.springframework.stereotype.Component;

/**
 * Classe de Implmentação do {@code mapper} de un {@link Cooperado}
 */
@Component
public class CooperadoMapper extends AbstractMapper<CooperadoResponse, CooperadoInput, Cooperado> {
}
