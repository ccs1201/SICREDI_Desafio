package br.com.ccs.sicredi.domain.service;

import br.com.ccs.sicredi.domain.entity.SessaoVotacao;
import br.com.ccs.sicredi.domain.entity.Voto;
import br.com.ccs.sicredi.domain.exception.service.BusinessLogicException;
import br.com.ccs.sicredi.domain.repository.VotoRepository;
import br.com.ccs.sicredi.domain.service.externalResources.EleitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class VotoService {

    private final OffsetDateTime now = OffsetDateTime.now();

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

        //Verifica se a sessão esta aberta para votos
        this.verificaSeSessaoEstaAbertaParaVoto(
                voto.getSessaoVotacao().getDataAbertura(),
                voto.getSessaoVotacao().getDataEncerramento());

        this.verificaSeEleitorJaVotouNaSessao(voto);

        this.verificaSeEleitorPodeVotar(voto.getCooperado().getCpf());

        repository.save(voto);
    }

    private void verificaSeSessaoEstaAbertaParaVoto(OffsetDateTime dataAbertura, OffsetDateTime dataEncerramento) {

        if (dataAbertura != null && dataAbertura.isBefore(now)) {

            if (now.isAfter(dataEncerramento)) {
                throw new BusinessLogicException("Sessão esta encerrada e não pode receber votos.");
            }

        }

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
