package br.com.ccs.sicred.api.v1.model.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@JsonRootName("pauta")
@Getter
@Setter
public class PautaIdInput {

    @NotNull
    @Positive
    private Long id;
}
