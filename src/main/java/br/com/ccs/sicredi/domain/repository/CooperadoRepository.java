package br.com.ccs.sicredi.domain.repository;

import br.com.ccs.sicredi.domain.entity.Cooperado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CooperadoRepository extends JpaRepository<Cooperado, Long>, JpaSpecificationExecutor<Cooperado> {
    /**
     * <p><b>Busca um cooperado pelo número do CPF</b></p>
     *
     * @param cpf o CPF do cooperado.
     * @return {@link Optional}
     */
    Optional<Cooperado> findByCpf(String cpf);
}