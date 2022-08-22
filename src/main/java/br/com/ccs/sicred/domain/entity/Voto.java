package br.com.ccs.sicred.domain.entity;

import br.com.ccs.sicred.domain.event.VotoRealizadoEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.Objects;

/**
 * <p><b>Classe que representa o voto de um
 * * Cooperado.</b></p>
 *
 * <p>Um cooperado somente poderá votar
 * uma vez por {@link SessaoVotacao} e
 * uma vez seu voto registrado não poderá ser
 * alterado.</p>
 *
 * <p>Para que não seja possível alterar um voto
 * o atributo {@link Boolean votoSim} foi setado
 * como updatable = "false"</p>
 *
 * <p><b> votoSim = True significa que o eleitor concorda e aprova a pauta</b></p>
 *
 * <p><b> votoSim = False significa que o eleitor NÃO concorda e NÃO aprova a pauta</b></p>
 *
 * @author Cleber Souza
 * @version 1.0
 * @since 20/08/2022
 */
@Getter
@Setter
@Entity
public class Voto extends AbstractAggregateRoot<Voto> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SessaoVotacao sessaoVotacao;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cooperado cooperado;

    @Setter(AccessLevel.NONE)
    @Column(updatable = false)
    private Boolean votouSim;

    public void votarSim() {
        this.votouSim = true;

        registerEvent(new VotoRealizadoEvent(this));
    }

    public void votarNao() {
        this.votouSim = false;

        registerEvent(new VotoRealizadoEvent(this));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voto voto = (Voto) o;
        return id.equals(voto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}