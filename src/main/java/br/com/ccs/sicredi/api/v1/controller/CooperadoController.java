package br.com.ccs.sicredi.api.v1.controller;

import br.com.ccs.sicredi.api.v1.utils.ApiConstants;
import br.com.ccs.sicredi.api.v1.model.representation.input.CooperadoInput;
import br.com.ccs.sicredi.api.v1.model.representation.response.CooperadoResponse;
import br.com.ccs.sicredi.core.utils.mapper.CooperadoMapper;
import br.com.ccs.sicredi.domain.entity.Cooperado;
import br.com.ccs.sicredi.domain.entity.Eleitor;
import br.com.ccs.sicredi.domain.exception.service.BusinessLogicException;
import br.com.ccs.sicredi.domain.service.CooperadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
@RequestMapping(ApiConstants.URI_V1 + "cooperado")
@AllArgsConstructor
public class CooperadoController {

    private static final String URL_EXTERNA = "https://user-info.herokuapp.com/users/";

    private CooperadoMapper mapper;
    private CooperadoService service;
    private RestTemplate restTemplate;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Retorna todos os Cooperados. Com Paginação")
    public Page<CooperadoResponse> getAll(@PageableDefault(size = 10) Pageable pageable) {
        return mapper.toPage(service.getAll(pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Insere um novo Cooperado")
    public CooperadoResponse save(@RequestBody @Valid CooperadoInput cooperado) {
        Cooperado cooperadoSalvo = service.save(mapper.toEntity(cooperado));
        return mapper.toResponseModel(cooperadoSalvo);
    }

    @GetMapping("/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Busca um Cooperado pelo CPF. Use somente números sem pontos ('.') ou traços ('-')")
    @Parameter(name = "cpf", description = "Número do CPF para realizar a consulta. Somente números.")
    public CooperadoResponse getByCpf(@PathVariable String cpf) {
        return mapper.toResponseModel(service.getByCpf(cpf));
    }

    @GetMapping("/external/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description =
            "Integra com um sistema externo (https://user-info.herokuapp.com/users/{cpf}) que verifica, a partir do CPF do associado, se ele pode votar"
            , deprecated = true)
    @Parameter(name = "cpf", description = "O CPF a ser consultado se pode ou não votar.")
    public Eleitor getExternal(@PathVariable String cpf) {

        Eleitor response;
        try {
            response = restTemplate.getForObject(URL_EXTERNA + cpf, Eleitor.class);
        } catch (HttpClientErrorException e) {
            throw new BusinessLogicException("CPF Não Encontrado", e);
        }

        return response;

    }
}
