package br.com.ccs.sicredi.core.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe resposável por fornecer os beans de
 * {@link ModelMapper} para serem injetados em
 * nossos mapper customizados.
 *
 * @author Cleber Souza
 * @version 1.0
 * @since 20/08/2022
 */

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
