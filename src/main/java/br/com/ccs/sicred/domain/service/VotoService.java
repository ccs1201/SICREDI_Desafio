package br.com.ccs.sicred.domain.service;

import br.com.ccs.sicred.domain.entity.SessaoVotacao;
import br.com.ccs.sicred.domain.entity.Voto;
import br.com.ccs.sicred.domain.exception.service.BusinessLogicException;
import br.com.ccs.sicred.domain.repository.VotoRepository;
import br.com.ccs.sicred.domain.service.externalResources.EleitorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class VotoService {

    public VotoService(VotoRepository repository,
                       EleitorService eleitorService,
                       SessaoVotacaoService sessaoVotacaoService,
                       CooperadoService cooperadoService) {

        this.repository = repository;
        this.eleitorService = eleitorService;
        this.sessaoVotacaoService = sessaoVotacaoService;
        this.cooperadoService = cooperadoService;
    }

    private final VotoRepository repository;
    private Voto voto;
    private final EleitorService eleitorService;
    private final SessaoVotacaoService sessaoVotacaoService;
    private final CooperadoService cooperadoService;

    /**
     * <p><b>Registra uma voto SIM na pauta com ID informado</b></p>
     *
     * @param pautaId O ID da pauta
     */
    @Transactional
    public void votarSimNaPauta(Long pautaId, String cpfEleitor) {

        voto = new Voto();
        voto.setCooperado(cooperadoService.getByCpf(cpfEleitor));
        voto.votarSim();
        this.save(voto, pautaId);
    }


    /**
     * <p><b>Registra uma voto NÃO na pauta com ID informado</b></p>
     *
     * @param pautaId O ID da Pauta
     */
    @Transactional
    public void votarNaoNaPauta(Long pautaId, String cpfEleitor) {

        voto = new Voto();
        voto.setCooperado(cooperadoService.getByCpf(cpfEleitor));
        voto.votarNao();
        this.save(voto, pautaId);

    }

    private void save(Voto voto, Long pautaId) {

        voto.setSessaoVotacao(this.getSessaoVotacaoPeloIdPauta(pautaId));

        OffsetDateTime abertura = voto.getSessaoVotacao().getDataAbertura();
        OffsetDateTime encerramento = voto.getSessaoVotacao().getDataEncerramento();

        //Verifica se a sessão esta aberta para votos
        if (abertura != null && abertura.isBefore(OffsetDateTime.now())) {
            if (encerramento.isAfter(OffsetDateTime.now())) {
                throw new BusinessLogicException("Sessão esta fechada e não pode receber votos.");
            }

        }

        this.verificaSeEleitorJaVotouNaSessao(voto);

        this.verificaSeEleitorPodeVotar(voto.getCooperado().getCpf());

        repository.save(voto);
    }

    /**
     * <p><b>Busca a sessão de votação pelo ID da Pauta</b></p>
     *
     * @param pautaId o id da Pauta em sessão.
     * @return {@link SessaoVotacao}
     * @throws BusinessLogicException se a sessão ja estiver encerrada.
     */

    private SessaoVotacao getSessaoVotacaoPeloIdPauta(Long pautaId) {

        return sessaoVotacaoService.getByPautaIdEager(pautaId);
    }

    /**
     * <p><b>Verifica se o Eleitor pode votar</b></p>
     *
     * @param cpfEleitor O CPF do Eleitor
     */
    private void verificaSeEleitorPodeVotar(String cpfEleitor) {
        eleitorService.isAbleToVote(cpfEleitor);
    }

    /**
     * <p><b>Verifica se o Eleitor já votou nesta Pauta</b></p>
     *
     * @param voto O voto do Eleitor
     */

    private void verificaSeEleitorJaVotouNaSessao(Voto voto) {

        var jaVotou = voto.getSessaoVotacao()
                .getVotos()
                .stream()
                .anyMatch(v ->
                        v.getCooperado().getCpf()
                                .equals(voto.getCooperado().getCpf()));

        if (jaVotou) {
            throw new BusinessLogicException("Eleitor já votou nesta Pauta.");
        }
    }
}
