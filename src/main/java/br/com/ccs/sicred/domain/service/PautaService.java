package br.com.ccs.sicred.domain.service;


import br.com.ccs.sicred.domain.entity.Pauta;
import br.com.ccs.sicred.domain.exception.service.BusinessLogicException;
import br.com.ccs.sicred.domain.exception.service.EntityPersistException;
import br.com.ccs.sicred.domain.exception.service.EntityUpdateException;
import br.com.ccs.sicred.domain.exception.service.PautaNotFoundException;
import br.com.ccs.sicred.domain.repository.PautaRepository;
import br.com.ccs.sicred.domain.repository.SessaoVotacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p><b>Classe de serviço para uma {@link Pauta}</b></p>
 * <p>Contem as regras de negócio relativas a uma Pauta a ser posta em votação</p>
 *
 * @author Cleber Souza
 * @version 1.0
 * @since 22/08/2022
 */
@Service
@AllArgsConstructor
public class PautaService {

    private final PautaRepository repository;
    private final SessaoVotacaoRepository sessaoVotacaoRepository;


    /**
     * <p><b>Recupera uma única {@link Pauta} pelo seu ID.</b></p>
     *
     * @param id O id da Pauta a ser localizada.
     * @return {@link Pauta}
     * @throws PautaNotFoundException caso nenhuma pauta seja localizada.
     */
    public Pauta getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PautaNotFoundException(id));
    }

    /**
     * <p><b>Recupera todas as {@link Pauta} cadastradas com paginação</b></p>
     *
     * @param pageable {@link Pageable} que contém as regras de paginação.
     * @return {@link Page} Contendo as pautas cadastradas e os dados de paginação.
     * @throws PautaNotFoundException caso nenhuma pauta seja localizada.
     */
    public Page<Pauta> getAll(Pageable pageable) {
        Page<Pauta> pautas = repository.findAll(pageable);
        if (pautas.isEmpty()) {
            throw new PautaNotFoundException();
        }

        return pautas;
    }

    /**
     * <p><b>Busca uma {@link Pauta} cuja o título conteha {@code titulo}</b></p>
     *
     * <p> {@code select * from pauta where titulo like %tiulo%}</p>
     *
     * @param titulo   O titulo ou parte dele, pelo qual devemos buscar a pauta.
     * @param pageable O pageable que representa os dados para paginação.
     * @return {@link Page}
     * @throws PautaNotFoundException quando uma pauta não for localizada.
     */
    public Page<Pauta> getByTituloPauta(String titulo, Pageable pageable) {
        Page<Pauta> pautas = repository.findByTituloPautaContaining(titulo, pageable);

        if (pautas.isEmpty()) {
            throw new PautaNotFoundException(titulo);
        }
        return pautas;
    }

    /**
     * <p><b>Salva uma {@link Pauta} no banco de dados</b></p>
     *
     * @param pauta Pauta a ser persistida.
     * @return {@link Pauta} - Fora do contexto transacional(detached).
     * @throws EntityPersistException Caso ocorra algum erro ao salvar.
     */
    @Transactional
    public Pauta save(Pauta pauta) {
        try {
            return repository.save(pauta);
        } catch (IllegalArgumentException e) {
            throw new EntityPersistException("Pauta", e);
        }
    }

    /**
     * <p><b>Atualiza uma {@link Pauta} existente. </b></p>
     *
     * @param pautaId ID da Pauta que sera atualizada.
     * @param pauta   Pauta que a ser atualizada.
     * @return {@link Pauta} A pauta atualizada.
     * @throws PautaNotFoundException Caso não seja encontrada uma pauta com o ID informado.
     * @throws EntityPersistException caso ocorra algum erro ao salvar.
     */
    @Transactional
    public Pauta update(Long pautaId, Pauta pauta) {

        var sessao = sessaoVotacaoRepository.findByPautaIdEager(pautaId);

        // verifca se já existe uma sessão para pauta,
        // pois caso exista se estiver aberta, contiver uma
        // data de abertura, então não podemos permitir i update
        if (sessao.isPresent()) {
            if (sessao.get().getDataAbertura() != null) {
                throw new BusinessLogicException("Pauta já foi aberta e não pode ser alterada.");
            }
        }

        Pauta pautaToUpdate = this.getById(pautaId);

        BeanUtils.copyProperties(pauta, pautaToUpdate, "id");

        try {
            return repository.save(pautaToUpdate);
        } catch (IllegalArgumentException e) {
            throw new EntityUpdateException("Pauta", e);
        }
    }
}
