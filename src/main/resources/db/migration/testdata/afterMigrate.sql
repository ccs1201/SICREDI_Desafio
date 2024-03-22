delete from cooperado;
delete from sessao_votacao;
delete from pauta;


insert into cooperado (cpf)
values ('70168535351'),
       ('69126782464'),
       ('01430395125'),
       ('55762711730'),
       ('65527426819'),
       ('58391865703'),
       ('48261406571');

insert into pauta (id, descricao_pauta, titulo_pauta)
values (1,'pauta 1', 'pauta 1');

insert into sessao_votacao (data_abertura, data_encerramento, duracao_em_minutos_apos_abertura, total_votos_nao,
                            total_votos_sim, pauta_id)
values (null, null, 1, 0, 0, 1);