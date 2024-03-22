package br.com.ccs.sicredi.domain.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.client.AutoConfigureMockWebServiceServer;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockWebServiceServer
class VotoServiceTest {

    @Autowired
    VotoService service;

    @Test
    void votarSimNaPauta() {

        service.votarSimNaPauta(1L, "70168535351");

    }

    @Test
    void votarNaoNaPauta() {
        service.votarNaoNaPauta(1L, "69126782464");
    }
}