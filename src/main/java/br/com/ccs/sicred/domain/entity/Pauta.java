package br.com.ccs.sicred.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * <p>
 * <b>
 * Classe que Representa uma Pauta a ser colocada em
 * Votação.
 * </b>
 * </p>
 *
 * @author Cleber Souza
 * @version 1.0
 * @since 20/08/2022
 */
@Getter
@Setter
@DynamicUpdate
@Entity
public class Pauta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String tituloPauta;

    @NotBlank
    @Column(nullable = false)
    private String descricaoPauta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pauta pauta = (Pauta) o;
        return id.equals(pauta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
