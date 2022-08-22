package br.com.ccs.sicred.domain.event.listener;

import br.com.ccs.sicred.domain.entity.SessaoVotacao;
import br.com.ccs.sicred.domain.event.VotoRealizadoEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * <p><b>Classe que observa sempre que um voto é registrado.</b></p>
 */
@Component
@AllArgsConstructor
public class VotoRealizadoListener {

    /**
     * <p><b>Computa os Votos da Sessão de votação sempre que um voto e realizado
     * antes do COMMIT da transação</b></p>
     *
     * @param votoRealizadoEvent Evento disparado sempre que um voto é computado.
     */

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onVotoRegistrado(VotoRealizadoEvent votoRealizadoEvent) {

        Boolean votouSim = votoRealizadoEvent.getVoto().getVotouSim();
        SessaoVotacao sessaoVotacao = votoRealizadoEvent.getVoto().getSessaoVotacao();

        if (votouSim) {
            sessaoVotacao.acrescerVotoSim();
        } else {
            sessaoVotacao.acrescerVotoNao();
        }

    }
}
