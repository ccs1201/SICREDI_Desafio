package br.com.ccs.sicred.core.utils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

/**
 * <p><b>Classe {@code abstract} que ser de base para os {@code Mappers} de Entidades do domínio.</b></p>
 *
 * @param <RESPONSEMODEL> Classe que representa um RESPONSE de uma Entidade de Domínio.
 * @param <INPUTMODEL>    Classe que representa um INPUT de uma Entidade de Domínio.
 * @param <ENTITY>        Classe que representa um RESPONSE de uma Entidade de Domínio.
 */
public abstract class AbstractMapper<RESPONSEMODEL, INPUTMODEL, ENTITY> implements MapperInterface<RESPONSEMODEL, INPUTMODEL, ENTITY> {

    @Autowired
    protected ModelMapper mapper;
    private final Class<RESPONSEMODEL> responseModelClass;
    private final Class<ENTITY> entityClass;

    public AbstractMapper() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();

        this.responseModelClass = (Class<RESPONSEMODEL>) type.getActualTypeArguments()[0];

        this.entityClass = (Class<ENTITY>) type.getActualTypeArguments()[2];
    }

    public RESPONSEMODEL toResponseModel(ENTITY entity) {

        return mapper.map(entity, responseModelClass);
    }


    public ENTITY toEntity(INPUTMODEL inputmodel) {
        return mapper.map(inputmodel, entityClass);
    }

    @Override
    public void updateEntity(INPUTMODEL inputmodel, ENTITY entity) {
        mapper.map(inputmodel, entity);
    }

    protected ModelMapper getMapper() {
        return this.mapper;
    }
}
