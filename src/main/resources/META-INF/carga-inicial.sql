insert into classe (id, nome) values (1, "Nomeação");
insert into classe (id, nome) values (2, "Utilidade Pública");
insert into classe (id, nome) values (3, "Saúde");
insert into classe (id, nome) values (4, "Acessibilidade");
insert into classe (id, nome) values (5, "Data comemorativa");
insert into classe (id, nome) values (6, "Educação");
insert into classe (id, nome) values (7, "Outros");

insert into partido_politico (id, sigla, nome) values (1, "PMDB", "PARTIDO DO MOVIMENTO DEMOCRÁTICO BRASILEIRO");
insert into partido_politico (id, sigla, nome) values (2, "PTB", "PARTIDO TRABALHISTA BRASILEIRO");
insert into partido_politico (id, sigla, nome) values (3, "PDT", "PARTIDO DEMOCRÁTICO TRABALHISTA");
insert into partido_politico (id, sigla, nome) values (4, "PT", "PARTIDO DOS TRABALHADORES");
insert into partido_politico (id, sigla, nome) values (5, "DEM", "DEMOCRATAS");
insert into partido_politico (id, sigla, nome) values (6, "PCdoB", "PARTIDO COMUNISTA DO BRASIL");
insert into partido_politico (id, sigla, nome) values (7, "PSB", "PARTIDO SOCIALISTA BRASILEIRO");
insert into partido_politico (id, sigla, nome) values (8, "PSDB", "PARTIDO DA SOCIAL DEMOCRACIA BRASILEIRA");
insert into partido_politico (id, sigla, nome) values (9, "PTC", "PARTIDO TRABALHISTA CRISTÃO");
insert into partido_politico (id, sigla, nome) values (10, "PSC", "PARTIDO SOCIAL CRISTÃO");
insert into partido_politico (id, sigla, nome) values (11, "PMN", "PARTIDO DA MOBILIZAÇÃO NACIONAL");
insert into partido_politico (id, sigla, nome) values (12, "PRP", "PARTIDO REPUBLICANO PROGRESSISTA");
insert into partido_politico (id, sigla, nome) values (13, "PPS", "PARTIDO POPULAR SOCIALISTA");
insert into partido_politico (id, sigla, nome) values (14, "PV", "PARTIDO VERDE");
insert into partido_politico (id, sigla, nome) values (15, "PTdoB", "PARTIDO TRABALHISTA DO BRASIL");
insert into partido_politico (id, sigla, nome) values (16, "PP", "PARTIDO PROGRESSISTA");
insert into partido_politico (id, sigla, nome) values (17, "PSTU", "PARTIDO SOCIALISTA DOS TRABALHADORES UNIFICADO");
insert into partido_politico (id, sigla, nome) values (18, "PCB", "PARTIDO COMUNISTA BRASILEIRO");
insert into partido_politico (id, sigla, nome) values (19, "PRTB", "PARTIDO RENOVADOR TRABALHISTA BRASILEIRO");
insert into partido_politico (id, sigla, nome) values (20, "PHS", "PARTIDO HUMANISTA DA SOLIDARIEDADE");
insert into partido_politico (id, sigla, nome) values (21, "PSDC", "PARTIDO SOCIAL DEMOCRATA CRISTÃO");
insert into partido_politico (id, sigla, nome) values (22, "PCO", "PARTIDO DA CAUSA OPERÁRIA");
insert into partido_politico (id, sigla, nome) values (23, "PODE", "PODEMOS");
insert into partido_politico (id, sigla, nome) values (24, "PSL", "PARTIDO SOCIAL LIBERAL");
insert into partido_politico (id, sigla, nome) values (25, "PRB", "PARTIDO REPUBLICANO BRASILEIRO");
insert into partido_politico (id, sigla, nome) values (26, "PSOL", "PARTIDO SOCIALISMO E LIBERDADE");
insert into partido_politico (id, sigla, nome) values (27, "PR", "PARTIDO DA REPÚBLICA");
insert into partido_politico (id, sigla, nome) values (28, "PSD", "PARTIDO SOCIAL DEMOCRÁTICO");
insert into partido_politico (id, sigla, nome) values (29, "PPL", "PARTIDO PÁTRIA LIVRE");
insert into partido_politico (id, sigla, nome) values (30, "PEN", "PARTIDO ECOLÓGICO NACIONAL");
insert into partido_politico (id, sigla, nome) values (31, "PROS", "PARTIDO REPUBLICANO DA ORDEM SOCIAL");
insert into partido_politico (id, sigla, nome) values (32, "SD", "SOLIDARIEDADE");
insert into partido_politico (id, sigla, nome) values (33, "NOVO", "PARTIDO NOVO");
insert into partido_politico (id, sigla, nome) values (34, "REDE", "REDE SUSTENTABILIDADE");
insert into partido_politico (id, sigla, nome) values (35, "PMB", "PARTIDO DA MULHER BRASILEIRA");

insert into tipo (id, nome, descricao) values (1, "Projeto de Lei", "Proposta por um(a) vereador(a) ou pela Prefeitura, visa criar nova lei ou alterar leis já existentes. Discutida na Câmara, poderá ser aprovada ou rejeitada.");
insert into tipo (id, nome, descricao) values (2, "Projeto de Decreto Legislativo", "Projeto destinado a disciplinar assuntos que excedam ao âmbito da Câmara, tais como fixação de subsídios e aprovação das contas da Prefeitura. Este independe de sanção do prefeito.");
insert into tipo (id, nome, descricao) values (3, "Projeto De Emenda À Lei Orgânica Municipal", "Projeto que visa alterar a Lei Orgânica Municipal (a “Constituição” do Município). Também exige quórum qualificado.");
insert into tipo (id, nome, descricao) values (4, "Requerimento", "Instrumento de fiscalização, é apresentado por vereador(a) e tem por objetivo solicitar informações a Prefeitura ou a algum outro órgão público, acerca da gestão, investimentos ou políticas públicas.");
insert into tipo (id, nome, descricao) values (5, "Projeto de Lei Complementar", "Da mesma forma que o projeto de lei, visa criar uma nova lei ou alterar lei já existente. Exige quórum qualificado (mais votos) para sua aprovação.");
insert into tipo (id, nome, descricao) values (6, "Projeto de Resolução", "Projeto destinado a disciplinar assuntos internos da Câmara, tais como política administrativa, de pessoal, alteração do Regimento Interno e outros.");
insert into tipo (id, nome, descricao) values (7, "Indicação", "Instrumento através do qual o(a) vereador(a) sugere ou solicita a Prefeitura a realização de determinados serviços públicos.");
insert into tipo (id, nome, descricao) values (8, "Moção", "Proposta apresentada por vereador(a) congratulando-se ou repudiando atitudes ou ações de pessoas ou entidades ou ainda manifestando condolências.");
insert into tipo (id, nome, descricao) values (9, "Diversos", "");

insert into situacao_simplificada (id, nome, descricao) values (1, "Em tramitação", "São os processos que ainda não foram apreciados pelo plenário.");
insert into situacao_simplificada (id, nome, descricao) values (2, "Aprovado", "São os processos que obtiveram maioria de voto no plenário.");
insert into situacao_simplificada (id, nome, descricao) values (3, "Arquivado", "São os processos que foram retirados pelo autor ou rejeitados pelo plenário que não obtiveram a maioria de votos suficientes para a sua aprovação.");
insert into situacao_simplificada (id, nome, descricao) values (4, "Rejeitado", "São processos rejeitados");
insert into situacao_simplificada (id, nome, descricao) values (5, "Sem Situação", "Processo antigo sem situação cadastrada");
