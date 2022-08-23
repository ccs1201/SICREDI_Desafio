package br.com.ccs.sicredi.api.v1.model.representation.input;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@JsonRootName("pauta")
@Getter
@Setter
@NoArgsConstructor
public class PautaIdInput {

    @NotNull
    @Positive
    private Long id;
}
