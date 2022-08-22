package br.com.ccs.sicred.api.v1.controller;

import br.com.ccs.sicred.domain.entity.Pauta;
import br.com.ccs.sicred.domain.service.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/v1/pauta")
@AllArgsConstructor
public class PautaController {

    PautaService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Retorna uma Coleção de Pauta paginada")
    @Parameters({
            @Parameter(name = "page", description = "Número da pagina que será retornada"),
            @Parameter(name = "size", description = "Quantidade de elementos por página."),
            @Parameter(name = "sort", description = "Atributo para ordenação"),
            @Parameter(name = "direction", description = "Sentido de ordenação ASC (Ascendente), DESC (Descendete)")
    })
    public Page<Pauta> getAll(@PageableDefault(size = 10) Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("{idPauta}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Retorna uma pauta pelo seu ID")
    public Pauta getById(@PathVariable @Positive Long idPauta) {
        return service.getById(idPauta);

    }

    @GetMapping(path = "/titulo", params = "titulo")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Localiza Pautas cujo título contenha em qualquer parte o parâmetro informado.")
    @Parameter(name = "titulo", description = "Palavra ou frase a ser encontrado no título da Pauta")
    public Page<Pauta> getByTitulo(@PageableDefault(size = 10) @Nullable Pageable pageable,
                                   @RequestParam @NotBlank String titulo) {
        return service.getByTituloPauta(titulo, pageable);
    }

    @PostMapping
    @Operation(description = "Salva uma Pauta")
    @ResponseStatus(HttpStatus.CREATED)
    public Pauta save(@RequestBody @Valid Pauta pauta) {
        return service.save(pauta);
    }

    @PutMapping("{pautaId}")
    @Operation(description = "Atualiza uma Pauta que ainda não esteja em votação")
    @Parameter(name = "pautaId", description = "ID da Pauta que será atualizada.")
    @ResponseStatus(HttpStatus.OK)
    public Pauta update(@PathVariable @Positive Long pautaId, @RequestBody @Valid Pauta pauta) {
        return service.update(pautaId, pauta);
    }

}
