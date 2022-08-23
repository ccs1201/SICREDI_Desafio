package br.com.ccs.sicredi.core.utils.mapper;

import br.com.ccs.sicredi.api.v1.model.representation.input.SessaoVotacaoInput;
import br.com.ccs.sicredi.api.v1.model.representation.response.ResultadoSessaoVotacaoResponse;
import br.com.ccs.sicredi.domain.entity.SessaoVotacao;
import org.springframework.stereotype.Component;

/**
 * <p><b>Implementacao do Mapper para exibir o resultado da votação de uma Pauta</b></p>
 */
@Component
public class ResultadoSessaoResponseMapper extends AbstractMapper<ResultadoSessaoVotacaoResponse, SessaoVotacaoInput, SessaoVotacao> {
}
