package br.com.ccs.sicred.core.utils.mapper;

import br.com.ccs.sicred.api.v1.model.model.input.SessaoVotacaoInput;
import br.com.ccs.sicred.api.v1.model.model.response.SessaoVotacaoResponse;
import br.com.ccs.sicred.domain.entity.SessaoVotacao;
import org.springframework.stereotype.Component;

@Component
public class SessaoVotacaoMapper extends AbstractMapper<SessaoVotacaoResponse, SessaoVotacaoInput, SessaoVotacao> {
}
