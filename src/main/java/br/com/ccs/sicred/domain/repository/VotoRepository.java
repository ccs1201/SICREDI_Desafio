package br.com.ccs.sicred.domain.repository;

import br.com.ccs.sicred.domain.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
}