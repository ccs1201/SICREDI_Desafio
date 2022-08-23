package br.com.ccs.sicredi.domain.service.externalResources;

import br.com.ccs.sicredi.domain.entity.Eleitor;
import br.com.ccs.sicredi.domain.exception.service.BusinessLogicException;
import br.com.ccs.sicredi.domain.exception.service.UnableToVoteException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class EleitorService {

    private static final String URL_EXTERNA = "https://user-info.herokuapp.com/users/";
    private RestTemplate restTemplate;

    /**
     * <p><b>Busca um eleitor no recurso externo</b></p>
     * <p>Verifica se o Eleitor existe pelo CPF e se esta apto a votar.</p>
     * <p> @see <a href="https://user-info.herokuapp.com/users/">URL_EXTERNA</a></p>
     *
     * @param cpf
     * @return {@link Eleitor}
     * @throws BusinessLogicException Caso o cpf não exista ou o Eleitor não possa votar.
     */
    public Eleitor getEleitor(String cpf) {

        Eleitor eleitor;
        try {
            eleitor = restTemplate.getForObject(URL_EXTERNA + cpf, Eleitor.class);
            isAbleToVote(eleitor);
            return eleitor;
        } catch (HttpClientErrorException e) {
            throw new BusinessLogicException("CPF Não Encontrado", e);
        }
    }

    /**
     * Verifica se o Eleitor pode votar
     *
     * @param eleitor
     * @return true se eleitor puder votar se não false.
     */
    public boolean isAbleToVote(Eleitor eleitor) {

        if (eleitor.isAbleToVote()) {
            return true;
        } else {
            throw new BusinessLogicException("Eleitor não autorizado a votar.");
        }
    }

    /**
     * Veririca se um eleitor pode votar pelo número do CPF.
     *
     * @param cpfEleitor
     * @return true se puder votar, senão false.
     */
    public boolean isAbleToVote(String cpfEleitor) {
        Eleitor eleitor = this.getEleitor(cpfEleitor);

        if (eleitor.isAbleToVote()) {
            return true;
        } else {
            throw new UnableToVoteException("Eleitor não autorizado a votar.");
        }
    }
}
