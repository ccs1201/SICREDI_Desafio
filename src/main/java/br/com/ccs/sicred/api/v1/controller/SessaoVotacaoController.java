package br.com.ccs.sicred.api.v1.controller;

import br.com.ccs.sicred.api.v1.model.model.input.SessaoVotacaoInput;
import br.com.ccs.sicred.api.v1.model.model.response.SessaoVotacaoResponse;
import br.com.ccs.sicred.core.utils.mapper.SessaoVotacaoMapper;
import br.com.ccs.sicred.domain.service.SessaoVotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/v1/sessao")
@AllArgsConstructor
public class SessaoVotacaoController {

    private SessaoVotacaoService service;
    private SessaoVotacaoMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Recupera uma coleção paginada de Sessão de Votação.")
    public Page<SessaoVotacaoResponse> getAll(@PageableDefault(size = 5) Pageable pageable) {

        return mapper.toPage(service.getAll(pageable));

    }

    @GetMapping("/abertas")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Recupera uma coleção paginada de sessão votação abertas para voto")
    public Page<SessaoVotacaoResponse> getAbertas(@PageableDefault(size = 5) Pageable pageable) {
        return mapper.toPage(service.getAbertas(pageable));
    }

    @GetMapping("/encerradas")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Recupera uma coleção paginada de sessão votação abertas para voto")
    public Page<SessaoVotacaoResponse> getEncerradas(@PageableDefault(size = 5) Pageable pageable) {
        return mapper.toPage(service.getEncerradas(pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Salva uma sessão de votação")
    public SessaoVotacaoResponse save(@Valid @RequestBody SessaoVotacaoInput sessaoVotacaoInput) {
        return mapper.toResponseModel(service.save(mapper.toEntity(sessaoVotacaoInput)));

    }

    @PatchMapping("/{sessaoId}/abre")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Abre uma sessão para votação")
    public void abrirSessaoParaVotação(@PathVariable @Positive Long sessaoId) {
        service.abrirSessaoParaVotacao(sessaoId);
    }
}
