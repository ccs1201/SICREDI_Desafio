package br.com.ccs.sicred.domain.repository;


import br.com.ccs.sicred.domain.entity.Pauta;
import br.com.ccs.sicred.domain.entity.SessaoVotacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {

    @Query(value = "select s from SessaoVotacao s join fetch s.pauta p",
            countQuery = "select id from SessaoVotacao")
    Page<SessaoVotacao> findAllEager(Pageable pageable);

    @Query(value = "select s from SessaoVotacao s join fetch s.pauta where s.dataEncerramento > :now",
            countQuery = "select id from SessaoVotacao")
    Page<SessaoVotacao> findAbertas(Pageable pageable, OffsetDateTime now);

    @Query(value = "select s from SessaoVotacao s join fetch s.pauta where s.dataEncerramento < :now",
            countQuery = "select id from SessaoVotacao")
    Page<SessaoVotacao> findEncerradas(Pageable pageable, OffsetDateTime now);

    @Query("select s from SessaoVotacao s join fetch s.pauta p where p.id = :id")
    Optional<SessaoVotacao> findByPautaIdEager(Long id);

    Optional<Object> findByPauta(Pauta pauta);
}
