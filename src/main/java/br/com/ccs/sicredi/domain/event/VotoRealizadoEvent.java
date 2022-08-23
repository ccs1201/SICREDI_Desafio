package br.com.ccs.sicredi.domain.event;

import br.com.ccs.sicredi.domain.entity.Voto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p><b>Representa um evento {@link Voto} sempre que um voto for registrado.</b></p>
 */
@Getter
@AllArgsConstructor
public class VotoRealizadoEvent {
    private Voto voto;
}
