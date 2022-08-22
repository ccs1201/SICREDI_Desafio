package br.com.ccs.sicred.domain.service;

import br.com.ccs.sicred.domain.entity.SessaoVotacao;
import br.com.ccs.sicred.domain.entity.Voto;
import br.com.ccs.sicred.domain.exception.service.BusinessLogicException;
import br.com.ccs.sicred.domain.repository.VotoRepository;
import br.com.ccs.sicred.domain.service.externalResources.EleitorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VotoService {

    public VotoService(VotoRepository repository, EleitorService eleitorService, SessaoVotacaoService sessaoVotacaoService) {
        this.repository = repository;
        this.eleitorService = eleitorService;
        this.sessaoVotacaoService = sessaoVotacaoService;
    }

    private VotoRepository repository;
    private Voto voto;
    private EleitorService eleitorService;
    private SessaoVotacaoService sessaoVotacaoService;

    /**
     * <p><b>Registra uma voto SIM na pauta com ID informado</b></p>
     *
     * @param pautaId O ID da pauta
     */
    @Transactional
    public void votarSimNaPauta(Long pautaId, String cpfEleitor) {

        voto = new Voto();
        voto.setCpfEleitor(cpfEleitor);
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
        voto.setCpfEleitor(cpfEleitor);
        voto.votarNao();
        this.save(voto, pautaId);

    }

    private void save(Voto voto, Long pautaId) {

        this.verificaSeEleitorPodeVotar(voto.getCpfEleitor());

        voto.setSessaoVotacao(this.getSessaoVotacaoPeloIdPauta(pautaId));

        this.verificaSeEleitorJaVotouNaSessao(voto);

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
        eleitorService.getEleitor(cpfEleitor);
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
                .anyMatch(v -> v.getCpfEleitor().equals(voto.getCpfEleitor()));

        if (jaVotou) {
            throw new BusinessLogicException("Eleitor já votou nesta Pauta.");
        }
    }
}
