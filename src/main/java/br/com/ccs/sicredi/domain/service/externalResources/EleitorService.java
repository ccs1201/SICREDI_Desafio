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
     * @param cpf cpf do cooperado.
     * @return {@link Eleitor}
     * @throws BusinessLogicException Caso o cpf não exista.
     * @throws UnableToVoteException Caso o eleitor não possa votar.
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
     * @param eleitor um eleitor.
     * @return true se eleitor puder votar se não lança uma exception.
     * @throws UnableToVoteException caso o eleitor não possa votar.
     */
    public boolean isAbleToVote(Eleitor eleitor) {

        if (eleitor.isAbleToVote()) {
            return true;
        } else {
            throw new UnableToVoteException("Eleitor não autorizado a votar.");
        }
    }

    /**
     * Verifica se um eleitor pode votar pelo número do CPF.
     *
     * @param cpfEleitor cpf do Eleitor.
     * @return true se puder votar, senão Lança uma exception.
     * @throws UnableToVoteException caso o eleitor não possa votar.
     */
    public boolean isAbleToVote(String cpfEleitor) {
        var eleitor = this.getEleitor(cpfEleitor);

        if (eleitor.isAbleToVote()) {
            return true;
        } else {
            throw new UnableToVoteException("Eleitor não autorizado a votar.");
        }
    }
}
