package br.com.ccs.sicredi.domain.entity;

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
 * <p><b>Classe que representa a Sessão de Votação em uma Pauta</b></p>
 *
 * @author Cleber Souza
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
    private Long totalVotosSim = 0L;
    @PositiveOrZero
    private Long totalVotosNao = 0L;
    @Transient
    private Boolean Aprovada = null;
    private OffsetDateTime dataAbertura;
    private OffsetDateTime dataEncerramento;

    //Duração de uma sessão em minutos
    private long duracaoEmMinutosAposAbertura = 1L;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sessaoVotacao")
    private List<Voto> votos = new java.util.ArrayList<>();

    /**
     * <p><b>Abre a sessão para votação</b></p>
     *
     * <p> Seta o atirbuto {@code dataAbertura} para a data e hora atual {@code OffsetDateTime.now()}</p>
     * <p> Calcula o atribudo {@code dataEncerramento} {@code dataAbertura.plusMinutes(duracaoEmMinutosAposAbertura)} <br><br>
     * {@code dataEncerramento} = ({@code dataAbertura} + {@code duracaoEmMinutosAposAbertura})</p>
     */
    public void abrir() {

        this.dataAbertura = OffsetDateTime.now();
        this.dataEncerramento = dataAbertura.plusMinutes(duracaoEmMinutosAposAbertura);
    }

    /**
     * <p><b>Executa os processos de fechamento e encerramento da
     * {@link SessaoVotacao} e {@link Pauta} </b></p>
     * <br>
     * Aprovada:<br>
     * <p><b>TRUE</b> - se totalVotosSim > totalVotosNao senão <b>FALSE</b>.</p>
     */
    private void fechar() {

        this.Aprovada = this.totalVotosSim > totalVotosNao;
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
     * <p>Método de cCallBack para para executar a rotina de
     * encerramento da votação casa a Data de Encerramento
     * seja anterior a {@code OffsetDateTime.now()} </p>
     *
     * @see this.fechar()
     */
    @PostLoad
    private void postLoadCallBack() {

        if (dataEncerramento != null && dataEncerramento.isBefore(OffsetDateTime.now())) {
            this.fechar();
        }
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
