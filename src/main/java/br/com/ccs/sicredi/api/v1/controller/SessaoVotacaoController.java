package br.com.ccs.sicredi.api.v1.controller;

import br.com.ccs.sicredi.api.v1.utils.ApiConstants;
import br.com.ccs.sicredi.api.v1.model.representation.input.SessaoVotacaoInput;
import br.com.ccs.sicredi.api.v1.model.representation.response.SessaoVotacaoResponse;
import br.com.ccs.sicredi.core.utils.mapper.ResultadoSessaoResponseMapper;
import br.com.ccs.sicredi.core.utils.mapper.SessaoVotacaoMapper;
import br.com.ccs.sicredi.domain.service.SessaoVotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(ApiConstants.URI_V1 + "sessao")
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

    @GetMapping("/{pautaId}/resultado")
    @Operation(description = "Exibe o resultado da votação na pauta.")
    @Parameter(name = "pautaId", description = "O número da pauta que sera procurada.")
    @ResponseStatus(HttpStatus.OK)
    public SessaoVotacaoResponse getResultadoPauta(@PathVariable Long pautaId) {

        return mapper.toResponseModel(service.getResultado(pautaId));

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
    public void abrirSessaoParaVotacao(@PathVariable @Positive Long sessaoId) {
        service.abrirSessaoParaVotacao(sessaoId);
    }
}
