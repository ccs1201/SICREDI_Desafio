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
        this.getPauta().setAberta(true);
    }

    /**
     * Fecha a votação setando encerrada como TRUE,
     * aberta para voto como FALSE, e seta o atributo aberta
     * da pauta como FALSE e encerrada como TRUE<br>
     * <p>Uma votação somente será considerada aprovada se
     * obtiver a maioria dos votos SIM, metade + 1</p>
     * <br>
     * Aprovada:<br>
     * <p><b>TRUE</b> - se totalVotosSim > totalVotosNao senão <b>FALSE</b>.</p>
     */
    private void fechar() {
        this.abertaParaVoto = false;
        this.encerrada = true;
        this.getPauta().setAberta(false);
        this.getPauta().setEncerrada(true);

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
