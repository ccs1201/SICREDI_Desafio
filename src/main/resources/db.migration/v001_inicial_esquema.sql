use sicred;

create table cooperado
(
    id  bigint auto_increment
        primary key,
    cpf varchar(11) null,
    constraint UK_8re5gjxbycfpswijjj4plu029
        unique (cpf)
)
    collate = utf8mb4_0900_ai_ci;

create table pauta
(
    id              bigint auto_increment
        primary key,
    descricao_pauta varchar(255)         not null,
    titulo_pauta    varchar(100)         not null,
    aberta          tinyint(1) default 0 not null
)
    collate = utf8mb4_0900_ai_ci;

create table sessao_votacao
(
    aprovada                         bit                  null,
    aberta_para_voto                 tinyint(1) default 0 not null,
    data_abertura                    datetime(6)          null,
    data_encerramento                datetime(6)          null,
    duracao_em_minutos_apos_abertura bigint               not null,
    encerrada                        tinyint(1) default 0 not null,
    total_votos_nao                  bigint               null,
    total_votos_sim                  bigint               null,
    pauta_id                         bigint               not null
        primary key,
    constraint FKf74f8sm5id28fb93vh3eew3ff
        foreign key (pauta_id) references pauta (id)
)
    collate = utf8mb4_0900_ai_ci;

create table voto
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
)
    collate = utf8mb4_0900_ai_ci;

