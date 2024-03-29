package br.com.ccs.sicredi.api.v1.controller;

import br.com.ccs.sicredi.api.v1.utils.ApiConstants;
import br.com.ccs.sicredi.domain.service.SessaoVotacaoService;
import br.com.ccs.sicredi.domain.service.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping(value = ApiConstants.URI_V1 + "voto/{cpfEleitor}")
@AllArgsConstructor
public class VotoController {

    private VotoService service;

    @PostMapping("{pautaId}/sim")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Registra um voto SIM na pauta informada. O cooperada precisa estar cadastrado para poder votar")
    public void votarSimPelaPauta(@PathVariable @CPF String cpfEleitor, @PathVariable @Positive Long pautaId) {
        service.votarSimNaPauta(pautaId, cpfEleitor);
    }

    @PostMapping("{pautaId}/nao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Registra um voto SIM na pauta informada. O cooperada precisa estar cadastrado para poder votar")
    public void votarNaoPelaPauta(@PathVariable @CPF String cpfEleitor, @PathVariable @Positive Long pautaId) {
        service.votarNaoNaPauta(pautaId, cpfEleitor);
    }
}
