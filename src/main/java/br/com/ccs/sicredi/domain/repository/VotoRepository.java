package br.com.ccs.sicredi.domain.repository;

import br.com.ccs.sicredi.domain.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
}