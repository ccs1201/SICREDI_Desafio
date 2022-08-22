package br.com.ccs.sicred.api.controller;

import br.com.ccs.sicred.domain.entity.Pauta;
import br.com.ccs.sicred.domain.repository.PautaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PautaControllerTest {

    private final String BASE_URI = "/api/v1/pauta/";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    PautaRepository repository;

    private Pauta pauta;

    @BeforeAll
    private void setup() {
        pauta = new Pauta();
        pauta.setTituloPauta("PautaControllerTest");
        pauta.setDescricaoPauta("PautaControllerTest");

    }

    @Test
    @DisplayName("Testa EndPoint '/' (getAll), deveria retorna 200 OK")
    void getAll() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testa EndPoint '/{pautaId}' deveria retorna 200 OK se o ID 1 existir ")
    void getById() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI + "1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Testa EndPoint '/{pautaId}' deveria retorna 400 BAD REQUEST para o ID -10 ")
    void getByIdInexistente() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI + "-10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Testa EndPoint '/titulo' deveria retorna 200 OK para o Titulo da Pauta contendo a palavra 'teste'")
    void getByTitulo() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI + "/titulo?titulo=teste")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testa EndPoint '/titulo' deveria retorna 400 BAD REQUEST para o Titulo da Pauta contendo a palavra 'AZAZAZA', POIS NÃO EXISTE")
    void getByTituloInexistente() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI + "/titulo?titulo=AZAZAZA")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Testa save ('POST') de uma Pauta, deveria retorna 200 OK ")
    void save() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.post(BASE_URI)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pauta)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Testa update (PUT) da Pauta ID 1, deveria retorna 200 OK")
    void update() throws Exception {

        //Busca a ultima pauta inserida no banco
        pauta = repository.findLastPautaInserted().get();

        //Alterando o titulo da pauta para atualização
        pauta.setTituloPauta("zzzzzzzzz");

        mockMvc.perform(RestDocumentationRequestBuilders.put(BASE_URI + pauta.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pauta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tituloPauta").value("zzzzzzzzz"));
    }
}