package br.com.ccs.sicredi.domain.repository;

import br.com.ccs.sicredi.domain.entity.Pauta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

    /**
     * <p><b>Busca uma pauta no banco que contenha em qualquer parte do seu
     * título o {@code titulo} passado como parâmetro.</b></p>
     *
     * @param titulo   Carácter, palavra ou texto que deve conter no título da pauta
     * @param pageable
     * @return {@link Page<Pauta>} contendo o resultado da pesquisa.
     * @author Cleber Souza
     * @since 21/0/2022
     */
    Page<Pauta> findByTituloPautaContaining(String titulo, Pageable pageable);

    /**
     * <p><b>Encontra a última {@link Pauta} cadastrada no banco de dados.</b></p>
     * <p>Irá retornar a Pauta que tiver maior ID</p>
     *
     * @return Optional
     */
    @Query("select p from Pauta p where p.id = (select max(p2.id) from Pauta p2)")
    Optional<Pauta> findLastPautaInserted();
}