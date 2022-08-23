package br.com.ccs.sicredi.domain.service;

import br.com.ccs.sicredi.domain.entity.Cooperado;
import br.com.ccs.sicredi.domain.exception.service.EntityAlreadyRegisteredException;
import br.com.ccs.sicredi.domain.exception.service.EntityPersistException;
import br.com.ccs.sicredi.domain.exception.service.RepositoryEntityNotFoundException;
import br.com.ccs.sicredi.domain.repository.CooperadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CooperadoService {

    CooperadoRepository repository;

    public Page<Cooperado> getAll(Pageable pageable) {
        Page<Cooperado> cooperados = repository.findAll(pageable);

        if (cooperados.isEmpty()) {
            throw new RepositoryEntityNotFoundException("Nenhum Cooperado localizado.");
        }

        return cooperados;
    }

    @Transactional
    public Cooperado save(Cooperado cooperado) {

        try {
            return repository.save(cooperado);

        } catch (IllegalArgumentException e) {
            throw new EntityPersistException("Cooperado", e);

        } catch (DataIntegrityViolationException e) {
            throw new EntityAlreadyRegisteredException(String.format("Cooperado com CPF: %s, já cadastrado.", cooperado.getCpf()), e);
        }
    }

    public Cooperado getByCpf(String cpf) {

        return repository.findByCpf(cpf).orElseThrow(() ->
                new RepositoryEntityNotFoundException(String.format("Cooperado com CPF: %s, não localizado.", cpf)));
    }
}
