package br.com.ccs.sicredi.domain.service;

import br.com.ccs.sicredi.domain.entity.SessaoVotacao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.client.AutoConfigureMockWebServiceServer;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockWebServiceServer
class SessaoVotacaoServiceTest {

    @Autowired
    private SessaoVotacaoService service;

    @Test
    void getByPauta() {
        SessaoVotacao sessao = service.getByPautaIdEager(1L);
        assertNotNull(sessao);

    }
}