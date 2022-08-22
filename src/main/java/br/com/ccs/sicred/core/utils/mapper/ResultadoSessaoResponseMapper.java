package br.com.ccs.sicred.core.utils.mapper;

import br.com.ccs.sicred.api.v1.model.representation.input.SessaoVotacaoInput;
import br.com.ccs.sicred.api.v1.model.representation.response.ResultadoSessaoVotacaoResponse;
import br.com.ccs.sicred.domain.entity.SessaoVotacao;
import org.springframework.stereotype.Component;

/**
 * <p><b>Implementacao do Mapper para exibir o resultado da votação de uma Pauta</b></p>
 */
@Component
public class ResultadoSessaoResponseMapper extends AbstractMapper<ResultadoSessaoVotacaoResponse, SessaoVotacaoInput, SessaoVotacao> {
}
