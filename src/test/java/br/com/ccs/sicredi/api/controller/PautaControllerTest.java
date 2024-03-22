package br.com.ccs.sicredi.api.controller;

import br.com.ccs.sicredi.api.v1.utils.ApiConstants;
import br.com.ccs.sicredi.domain.entity.Pauta;
import br.com.ccs.sicredi.domain.repository.PautaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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

    private final String BASE_URI = ApiConstants.URI_V1 + "pauta/";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private PautaRepository repository;

    private Pauta pauta;

    @BeforeAll
    public void setup() {
        pauta = new Pauta();
        pauta.setTituloPauta("PautaControllerTeste");
        pauta.setDescricaoPauta("PautaControllerTeste");

    }

    @Test
    @DisplayName("Testa save ('POST') de uma Pauta, deveria retorna 200 OK ")
    @Order(1)
    void save() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.post(BASE_URI)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pauta)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Testa EndPoint '/' (getAll), deveria retorna 200 OK")
    @Order(2)
    void getAll() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testa EndPoint '/{pautaId}' deveria retorna 200 OK se o ID 1 existir ")
    @Order(3)
    void getById() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI + "1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Testa EndPoint '/{pautaId}' deveria retorna 400 BAD REQUEST para o ID -10 ")
    @Order(4)
    void getByIdInexistente() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI + "-10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Testa EndPoint '/titulo' deveria retorna 200 OK para o Titulo da Pauta contendo a palavra 'test'")
    @Order(5)
    void getByTitulo() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI + "/titulo?titulo=test")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testa EndPoint '/titulo' deveria retorna 400 BAD REQUEST para o Titulo da Pauta contendo a palavra 'AZAZAZA', POIS NÃO EXISTE")
    @Order(6)
    void getByTituloInexistente() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI + "/titulo?titulo=AZAZAZA")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Testa update (PUT) da Pauta ID 1, deveria retorna 200 OK")
    @Order(7)
    void update() throws Exception {

        //Busca a ultima pauta inserida no banco
        pauta = repository.findLastPautaInserted().get();

        //Alterando o titulo da pauta para atualização
        pauta.setDescricaoPauta("zzzzzzzzz");

        mockMvc.perform(RestDocumentationRequestBuilders.put(BASE_URI + pauta.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pauta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricaoPauta").value("zzzzzzzzz"));
    }
}