package br.com.ccs.sicredi.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@DynamicUpdate
@Entity
public class Cooperado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @CPF
    @Column(unique = true, length = 11)
    private String cpf;


    public void setCpf(String cpf) {
        this.cpf = cpf;
        this.formatCpf(cpf);

    }

    @OneToMany(mappedBy = "cooperado", fetch = FetchType.LAZY)
    private Collection<Voto> votos;

    /**
     * <p><b>Formata o cpf do cooperado removendo '.' (Pontos), '-' (traços, hifén) e 'espaços em branco' (white spaces) </b></p>
     */
    public void formatCpf(String cpf) {
        cpf = cpf.trim();
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cooperado cooperado = (Cooperado) o;
        return id.equals(cooperado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}