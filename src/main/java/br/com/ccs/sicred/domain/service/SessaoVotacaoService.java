package br.com.ccs.sicred.domain.service;

import br.com.ccs.sicred.domain.entity.Pauta;
import br.com.ccs.sicred.domain.entity.SessaoVotacao;
import br.com.ccs.sicred.domain.exception.service.BusinessLogicException;
import br.com.ccs.sicred.domain.exception.service.EntityPersistException;
import br.com.ccs.sicred.domain.exception.service.RepositoryEntityNotFoundException;
import br.com.ccs.sicred.domain.repository.SessaoVotacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class SessaoVotacaoService {

    private SessaoVotacaoRepository repository;
    private PautaService pautaService;

    /**
     * <p><b> Recupera todas as sessões de votação com paginação</b></p>
     */
    public Page<SessaoVotacao> getAll(Pageable pageable) {
        var sessao = repository.findAllEager(pageable);

        if (sessao.isEmpty()) {
            throw new RepositoryEntityNotFoundException("Nenhuma sessão localizada.");
        }

        return sessao;
    }

    /**
     * <p><b> Recupera as sessões de votação abertas para voto</b></p>
     *
     * @param pageable
     * @return {@link Page}
     */
    public Page<SessaoVotacao> getAbertas(Pageable pageable) {

        var sessao = repository.findAbertas(pageable, OffsetDateTime.now());

        if (sessao.isEmpty()) {
            throw new RepositoryEntityNotFoundException("Nenhuma sessão localizada.");
        }

        return sessao;
    }

    public Page<SessaoVotacao> getEncerradas(Pageable pageable) {
        var sessao = repository.findEncerradas(pageable, OffsetDateTime.now());

        if (sessao.isEmpty()) {
            throw new RepositoryEntityNotFoundException("Nenhuma sessão localizada.");
        }

        return sessao;
    }

    /**
     * <p><b>Salva uma sessão de votação</b></p>
     *
     * <p>Toda nova sessão cadastrada começa com
     * a propriedade {@code sessaoVotacao.abertaPataVoto} como {@code false} e
     * {@code sessaoVotacao.aprovada} como {@code null}.<br>
     * Não podem haver duas sessão de votação para a mesma pauta.</p>
     *
     * @param sessaoVotacao
     * @return {@link SessaoVotacao}
     */
    @Transactional
    public SessaoVotacao save(SessaoVotacao sessaoVotacao) {
        try {

            //Confirma se a pauta existe e garante que o objeto pauta esteja
            //gerenciado pelo contexto de persistência (MANAGED)
            sessaoVotacao.setPauta(this.findPauta(sessaoVotacao.getPauta().getId()));

            //checa se ja existe uma sessão de votação para a pauta informada
            this.verificaSeJaExisteSessaoParaPauta(sessaoVotacao.getPauta());

            //Seta sessão como fechada para votação
            sessaoVotacao.setAbertaParaVoto(false);

            //Garante que uma sessão nova não tenha aprovação ou recusa.
            sessaoVotacao.setAprovada(null);

            return repository.save(sessaoVotacao);

        } catch (DataIntegrityViolationException e) {
            throw new EntityPersistException("sessão votação", e);

        } catch (IllegalArgumentException e) {
            throw new EntityPersistException("sessão de votação.", e);
        }
    }

    /**
     * <p><b>Verifica se já existe uma Sessão cadastrada para a pauta informada.</b></p>
     *
     * <p>Não é permitida mais de sessão de votação por pauta. Por isto
     * devemos verificar se já existe uma sessão e caso exista lançamos uma
     * {@link BusinessLogicException} notificando o usuário que não podem
     * haver duas sessões para a mesma pauta.</p>
     *
     * @param pauta
     * @throws BusinessLogicException caso já exista uma sessão para a pauta informada.
     */
    private void verificaSeJaExisteSessaoParaPauta(Pauta pauta) {

        repository.findByPauta(pauta).ifPresent((p) -> {
            throw new BusinessLogicException("Já existe uma sessão cadastrada para a pauta informada." + " Não podem existir duas sessões para a mesma pauta.");
        });

    }

    /**
     * <p><b>Busca uma {@link SessaoVotacao} pelo ID.</b></p>
     *
     * @param id o id da sessão a ser localizada
     * @return {@link SessaoVotacao}
     * @throws RepositoryEntityNotFoundException se nenhum objeto for localizado.
     */
    public SessaoVotacao getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new RepositoryEntityNotFoundException(String.format("Sessão de votação com ID: %d, não localizada", id)));
    }

    /**
     * <p><b>Confirma se a pauta existe e garante que o objeto pauta esteja
     * gerenciado pelo contexto de persistência (MANAGED) para evitar
     * {@link org.hibernate.PersistentObjectException} ao passar um objeto
     * relacionado fora do contexto da {@code JPA} ({@code DETACHED})</b></p>
     *
     * @param pautaId o id da Pauta.
     */
    private Pauta findPauta(Long pautaId) {

        return pautaService.getById(pautaId);

    }

    /**
     * <p><b>Abre uma sessão para votação.</b></p>
     *
     * @param sessaoId o id da sessão que será aberta.
     */
    @Transactional
    public void abrirSessaoParaVotacao(Long sessaoId) {

        SessaoVotacao sessaoVotacao = this.getById(sessaoId);

        //Se a sessão já estiver encerrada não pose ser reaberta
        if (sessaoVotacao.getEncerrada()) {
            throw new BusinessLogicException("Sessão já foi encerrada e não pode ser alterada.");

            //Se a sessão ja tiver uma data de abertura
            //então não pode ser alterada
        } else if (sessaoVotacao.getAbertaParaVoto()) {
            throw new BusinessLogicException("Sessão já esta aberta.");
        }

        sessaoVotacao.abrir();
        try {

            //SaveAndFlush realiza o commit imediatamente
            //sem aguardar pela decisão do ContextManager
            //que pode realizar o commit mais "tarde".
            repository.saveAndFlush(sessaoVotacao);
        } catch (IllegalArgumentException e) {
            throw new EntityPersistException("Sessão Votação.");
        }
    }

    /**
     * <p><b>Busca uma sessão pelo ID da pauta.</b></p>
     *
     * @param pautaId o id da pauta
     * @return SessaoVotacao
     */
    public SessaoVotacao getByPautaIdEager(Long pautaId) {

        return repository.findByPautaIdEager(pautaId)
                .orElseThrow(() ->
                        new RepositoryEntityNotFoundException("Não existe sessão para a pauta informada."));
    }
}
