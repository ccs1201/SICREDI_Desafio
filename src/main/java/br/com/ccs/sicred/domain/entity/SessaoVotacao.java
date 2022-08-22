package br.com.ccs.sicred.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p><b>Classe que representa a votação em uma Pauta</b></p>
 *
 * @author Cleber Souza
 * @version 1.0
 * @since 20/08/2022
 */
@Getter
@Setter
@DynamicUpdate
@Entity
public class SessaoVotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @Valid
    private Pauta pauta;
    @PositiveOrZero
    private Long totalVotosSim = Long.valueOf(0L);
    @PositiveOrZero
    private Long totalVotosNao = Long.valueOf(0L);

    private Boolean Aprovada = null;

    @Column(columnDefinition = "boolean not null default false")
    private Boolean abertaParaVoto = false;

    @Column(columnDefinition = "boolean not null default false")
    private Boolean encerrada = false;
    private OffsetDateTime dataAbertura;
    private OffsetDateTime dataEncerramento;

    //Duração de uma sessão em minutos
    private long duracaoEmMinutosAposAbertura = 1L;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sessaoVotacao")
    private List<Voto> votos = new java.util.ArrayList<>();

    /**
     * <p><b>Abre a sessão para votação</b></p>
     * <p> Seta o atributo {@code abertaParaVoto} como True</p>
     * <p> Seta o atirbuto {@code dataAbertura} para a data e hora atual {@code OffsetDateTime.now()}</p>
     * <p> Calcula o atribudo {@code dataEncerramento} {@code dataAbertura.plusMinutes(duracaoEmMinutosAposAbertura)} <br><br>
     * {@code dataEncerramento} = ({@code dataAbertura} + {@code duracaoEmMinutosAposAbertura})</p>
     */
    public void abrir() {
        this.abertaParaVoto = true;
        this.dataAbertura = OffsetDateTime.now();
        this.dataEncerramento = dataAbertura.plusMinutes(duracaoEmMinutosAposAbertura);
    }

    /**
     * Fecha a votação setando ecerrada como true
     * aberto para voto como false e <br>
     * aprovada:<br>
     * true - se totalVotosSim > totalVotosNao senão false.
     */
    private void fechar() {
        this.abertaParaVoto = false;
        this.encerrada = true;

        if (this.totalVotosSim > totalVotosNao) {
            this.Aprovada = true;
        } else {
            this.Aprovada = false;
        }
    }

    /**
     * <p>Adiciona um voto SIM</p>
     */
    public void acrescerVotoSim() {

        this.totalVotosSim++;
    }

    /**
     * <p>Adiciona  um voto NÃO</p>
     */
    public void acrescerVotoNao() {

        this.totalVotosNao++;
    }

    /**
     * <p>Atualiza o atributo {@code abertaParaVoto} caso esteja como true, após carregar do banco de dados.
     * Usado para setar {@code abertaParaVoto} para false, se a data de encerramento
     * for anterior a data atual.</p>
     */
    @PostLoad
    private void postLoadCallBack() {
        if (abertaParaVoto) {
            if (dataEncerramento.isBefore(OffsetDateTime.now())) {
                this.fechar();
            }
        }

//        if (this.dataEncerramento != null) {
//            this.setAbertaParaVoto(dataEncerramento.isBefore(OffsetDateTime.now()));
//        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessaoVotacao that = (SessaoVotacao) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
