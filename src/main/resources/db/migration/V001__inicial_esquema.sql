use sicred;

create table if not exists cooperado
(
    id  bigint auto_increment
        primary key,
    cpf varchar(11) null,
    constraint UK_8re5gjxbycfpswijjj4plu029
        unique (cpf)
);

create table if not exists pauta
(
    id              bigint auto_increment
        primary key,
    descricao_pauta varchar(255) not null,
    titulo_pauta    varchar(100) not null
);

create table if not exists sessao_votacao
(
    data_abertura                    datetime(6) null,
    data_encerramento                datetime(6) null,
    duracao_em_minutos_apos_abertura bigint      not null,
    total_votos_nao                  bigint      null,
    total_votos_sim                  bigint      null,
    pauta_id                         bigint      not null
        primary key,
    constraint FKf74f8sm5id28fb93vh3eew3ff
        foreign key (pauta_id) references pauta (id)
);

create table if not exists voto
(
    id                      bigint auto_increment
        primary key,
    votou_sim               bit    null,
    cooperado_id            bigint not null,
    sessao_votacao_pauta_id bigint not null,
    constraint FKm9netfs126ijfvf17edn11p90
        foreign key (cooperado_id) references cooperado (id),
    constraint FKnqunvxy0jeltpvq5gjbtxdnys
        foreign key (sessao_votacao_pauta_id) references sessao_votacao (pauta_id)
);

