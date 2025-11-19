------------------------------------------------------
-- DROP (opcional – só usar se precisar zerar tudo)
------------------------------------------------------
-- DROP TABLE TRILHA_SKILL_NECESSARIA CASCADE CONSTRAINTS;
-- DROP TABLE USUARIO_TRILHA CASCADE CONSTRAINTS;
-- DROP TABLE USUARIO_SKILL CASCADE CONSTRAINTS;
-- DROP TABLE USUARIO_AREA CASCADE CONSTRAINTS;
-- DROP TABLE CURSO_RECOMENDADO CASCADE CONSTRAINTS;
-- DROP TABLE TRILHA CASCADE CONSTRAINTS;
-- DROP TABLE SKILL CASCADE CONSTRAINTS;
-- DROP TABLE USUARIO CASCADE CONSTRAINTS;
-- DROP TABLE AREA CASCADE CONSTRAINTS;

------------------------------------------------------
-- TABELA: AREA
------------------------------------------------------
CREATE TABLE AREA (
    idArea      NUMBER(10)        NOT NULL,
    nome        VARCHAR2(100)     NOT NULL,
    descricao   VARCHAR2(255),
    dtCriacao   DATE              NOT NULL,
    CONSTRAINT PK_AREA PRIMARY KEY (idArea)
);

------------------------------------------------------
-- TABELA: USUARIO
------------------------------------------------------
CREATE TABLE USUARIO (
    idUsuario        NUMBER(10)     NOT NULL,
    nome             VARCHAR2(100)  NOT NULL,
    sobrenome        VARCHAR2(200)  NOT NULL,
    email            VARCHAR2(150)  NOT NULL,
    telefone         VARCHAR2(20),
    dtCadastro       DATE           NOT NULL,
    estado           CHAR(2),
    objetivoCarreira VARCHAR2(30)   NOT NULL,
    statusPerfil     VARCHAR2(20)   NOT NULL,
    CONSTRAINT PK_USUARIO PRIMARY KEY (idUsuario),
    CONSTRAINT UK_USUARIO_EMAIL UNIQUE (email)
);

------------------------------------------------------
-- TABELA: SKILL
------------------------------------------------------
CREATE TABLE SKILL (
    idSkill       NUMBER(10)      NOT NULL,
    nome          VARCHAR2(100)   NOT NULL,
    descricao     VARCHAR2(255),
    nivel         VARCHAR2(20)    NOT NULL,  -- ex: INICIANTE, INTERMEDIARIO
    categoria     VARCHAR2(20)    NOT NULL,  -- ex: TECNICA, COMPORTAMENTAL
    ultimoAcesso  DATE,
    tipo          VARCHAR2(10)    NOT NULL,  -- ex: HARD, SOFT
    CONSTRAINT PK_SKILL PRIMARY KEY (idSkill)
);

------------------------------------------------------
-- TABELA: TRILHA
------------------------------------------------------
CREATE TABLE TRILHA (
    idTrilha    NUMBER(10)       NOT NULL,
    nome        VARCHAR2(150)    NOT NULL,
    descricao   VARCHAR2(400),
    nivel       VARCHAR2(20)     NOT NULL,  -- ex: INICIANTE
    objetivo    VARCHAR2(255)    NOT NULL,
    status      VARCHAR2(20)     NOT NULL,  -- ex: EM_ANDAMENTO, NAO_INICIADA
    dtCriacao   DATE             NOT NULL,
    CONSTRAINT PK_TRILHA PRIMARY KEY (idTrilha)
);

------------------------------------------------------
-- TABELA: CURSO_RECOMENDADO
------------------------------------------------------
CREATE TABLE CURSO_RECOMENDADO (
    idCurso        NUMBER(10)      NOT NULL,
    nome           VARCHAR2(200)   NOT NULL,
    url            VARCHAR2(500)   NOT NULL,
    TRILHA_idTrilha NUMBER(10)     NOT NULL,
    plataforma     VARCHAR2(20)    NOT NULL, -- ex: ALURA, UDEMY
    duracaoHoras   NUMBER(4),
    CONSTRAINT PK_CURSO_RECOMENDADO PRIMARY KEY (idCurso),
    CONSTRAINT FK_CURSO_TRILHA FOREIGN KEY (TRILHA_idTrilha)
        REFERENCES TRILHA (idTrilha)
);

------------------------------------------------------
-- TABELA: USUARIO_AREA (N:N)
------------------------------------------------------
CREATE TABLE USUARIO_AREA (
    USUARIO_idUsuario  NUMBER(10) NOT NULL,
    AREA_idArea        NUMBER(10) NOT NULL,
    CONSTRAINT PK_USUARIO_AREA PRIMARY KEY (USUARIO_idUsuario, AREA_idArea),
    CONSTRAINT FK_USUARIO_AREA_USUARIO FOREIGN KEY (USUARIO_idUsuario)
        REFERENCES USUARIO (idUsuario),
    CONSTRAINT FK_USUARIO_AREA_AREA FOREIGN KEY (AREA_idArea)
        REFERENCES AREA (idArea)
);

------------------------------------------------------
-- TABELA: USUARIO_SKILL (N:N + nivel do usuário)
------------------------------------------------------
CREATE TABLE USUARIO_SKILL (
    USUARIO_idUsuario  NUMBER(10) NOT NULL,
    SKILL_idSkill      NUMBER(10) NOT NULL,
    nivel              VARCHAR2(20) NOT NULL, -- INICIANTE / INTERMEDIARIO...
    CONSTRAINT PK_USUARIO_SKILL PRIMARY KEY (USUARIO_idUsuario, SKILL_idSkill),
    CONSTRAINT FK_USUARIO_SKILL_USUARIO FOREIGN KEY (USUARIO_idUsuario)
        REFERENCES USUARIO (idUsuario),
    CONSTRAINT FK_USUARIO_SKILL_SKILL FOREIGN KEY (SKILL_idSkill)
        REFERENCES SKILL (idSkill)
);

------------------------------------------------------
-- TABELA: USUARIO_TRILHA (N:N + status e datas)
------------------------------------------------------
CREATE TABLE USUARIO_TRILHA (
    USUARIO_idUsuario  NUMBER(10) NOT NULL,
    TRILHA_idTrilha    NUMBER(10) NOT NULL,
    status             VARCHAR2(20) NOT NULL, -- EM_ANDAMENTO, CONCLUIDA...
    dataInicio         DATE,
    dataConclusao      DATE,
    CONSTRAINT PK_USUARIO_TRILHA PRIMARY KEY (USUARIO_idUsuario, TRILHA_idTrilha),
    CONSTRAINT FK_USUARIO_TRILHA_USUARIO FOREIGN KEY (USUARIO_idUsuario)
        REFERENCES USUARIO (idUsuario),
    CONSTRAINT FK_USUARIO_TRILHA_TRILHA FOREIGN KEY (TRILHA_idTrilha)
        REFERENCES TRILHA (idTrilha)
);

------------------------------------------------------
-- TABELA: TRILHA_SKILL_NECESSARIA (N:N)
------------------------------------------------------
CREATE TABLE TRILHA_SKILL_NECESSARIA (
    TRILHA_idTrilha  NUMBER(10) NOT NULL,
    SKILL_idSkill    NUMBER(10) NOT NULL,
    CONSTRAINT PK_TRILHA_SKILL_NECESSARIA PRIMARY KEY (TRILHA_idTrilha, SKILL_idSkill),
    CONSTRAINT FK_TSN_TRILHA FOREIGN KEY (TRILHA_idTrilha)
        REFERENCES TRILHA (idTrilha),
    CONSTRAINT FK_TSN_SKILL FOREIGN KEY (SKILL_idSkill)
        REFERENCES SKILL (idSkill)
);
------------------------------------------------------

-- ÁREAS
------------------------------------------------------
INSERT INTO AREA (idArea, nome, descricao, dtCriacao) VALUES
(1, 'Tecnologia da Informação', 'Carreiras em desenvolvimento de software e sistemas', DATE ''2025-01-05'');
INSERT INTO AREA (idArea, nome, descricao, dtCriacao) VALUES
(2, 'Dados & Analytics', 'Carreiras focadas em análise de dados e ciência de dados', DATE ''2025-01-06'');
INSERT INTO AREA (idArea, nome, descricao, dtCriacao) VALUES
(3, 'UX & Produto', 'Experiência do usuário, pesquisa e gestão de produto digital', DATE ''2025-01-07'');
INSERT INTO AREA (idArea, nome, descricao, dtCriacao) VALUES
(4, 'Cloud & DevOps', 'Infraestrutura em nuvem, automação e CI/CD', DATE ''2025-01-08'');
INSERT INTO AREA (idArea, nome, descricao, dtCriacao) VALUES
(5, 'Cibersegurança', 'Proteção de dados, redes e aplicações', DATE ''2025-01-09'');
INSERT INTO AREA (idArea, nome, descricao, dtCriacao) VALUES
(6, 'Finanças Digitais', 'Pagamentos, bancos digitais e criptoativos', DATE ''2025-01-10'');
INSERT INTO AREA (idArea, nome, descricao, dtCriacao) VALUES
(7, 'Saúde Digital', 'Tecnologias aplicadas à saúde e telemedicina', DATE ''2025-01-11'');
INSERT INTO AREA (idArea, nome, descricao, dtCriacao) VALUES
(8, 'Educação & Edtech', 'Plataformas e soluções de educação digital', DATE ''2025-01-12'');
INSERT INTO AREA (idArea, nome, descricao, dtCriacao) VALUES
(9, 'Sustentabilidade & ESG', 'Projetos voltados a impacto socioambiental', DATE ''2025-01-13'');
INSERT INTO AREA (idArea, nome, descricao, dtCriacao) VALUES
(10, 'Operações & Logística', 'Otimização de processos, transporte e cadeia de suprimentos', DATE ''2025-01-14'');


------------------------------------------------------
-- USUÁRIOS
------------------------------------------------------
INSERT INTO USUARIO (idUsuario, nome, sobrenome, email, telefone, dtCadastro, estado, objetivoCarreira, statusPerfil) VALUES
(1, 'Ana', 'Souza', 'ana.souza@example.com', '11999990001', DATE ''2025-02-01'', 'SP', 'MIGRAR_AREA', 'ATIVO');
INSERT INTO USUARIO VALUES
(2, 'Bruno', 'Lima', 'bruno.lima@example.com', '21999990002', DATE ''2025-02-02'', 'RJ', 'PRIMEIRO_EMPREGO', 'ATIVO');
INSERT INTO USUARIO VALUES
(3, 'Carla', 'Oliveira', 'carla.oliveira@example.com', '31999990003', DATE ''2025-02-03'', 'MG', 'CRESCER_CARREIRA', 'ATIVO');
INSERT INTO USUARIO VALUES
(4, 'Diego', 'Santos', 'diego.santos@example.com', '41999990004', DATE ''2025-02-04'', 'PR', 'MIGRAR_AREA', 'INCOMPLETO');
INSERT INTO USUARIO VALUES
(5, 'Eduarda', 'Mendes', 'eduarda.mendes@example.com', '51999990005', DATE ''2025-02-05'', 'RS', 'RECOLOCACAO', 'ATIVO');
INSERT INTO USUARIO VALUES
(6, 'Felipe', 'Costa', 'felipe.costa@example.com', '61999990006', DATE ''2025-02-06'', 'DF', 'MIGRAR_AREA', 'PAUSADO');
INSERT INTO USUARIO VALUES
(7, 'Gabriela', 'Ribeiro', 'gabriela.ribeiro@example.com', '71999990007', DATE ''2025-02-07'', 'BA', 'PRIMEIRO_EMPREGO', 'ATIVO');
INSERT INTO USUARIO VALUES
(8, 'Henrique', 'Almeida', 'henrique.almeida@example.com', '81999990008', DATE ''2025-02-08'', 'PE', 'CRESCER_CARREIRA', 'ATIVO');
INSERT INTO USUARIO VALUES
(9, 'Isabela', 'Martins', 'isabela.martins@example.com', '11999990009', DATE ''2025-02-09'', 'SP', 'RECOLOCACAO', 'ATIVO');
INSERT INTO USUARIO VALUES
(10, 'João', 'Pereira', 'joao.pereira@example.com', '21999990010', DATE ''2025-02-10'', 'RJ', 'MIGRAR_AREA', 'ATIVO');


------------------------------------------------------
-- SKILLS
------------------------------------------------------
INSERT INTO SKILL (idSkill, nome, descricao, nivel, categoria, ultimoAcesso, tipo) VALUES
(1, 'Java', 'Fundamentos de Java para desenvolvimento backend', 'INICIANTE', 'TECNICA', DATE ''2025-03-01'', 'HARD');
INSERT INTO SKILL VALUES
(2, 'Spring Boot', 'Criação de APIs REST com Spring Boot', 'INTERMEDIARIO', 'TECNICA', DATE ''2025-03-02'', 'HARD');
INSERT INTO SKILL VALUES
(3, 'SQL', 'Consultas em bancos relacionais (Oracle, PostgreSQL)', 'INTERMEDIARIO', 'TECNICA', DATE ''2025-03-03'', 'HARD');
INSERT INTO SKILL VALUES
(4, 'Git & Versionamento', 'Controle de versão com Git e GitHub', 'INICIANTE', 'TECNICA', DATE ''2025-03-04'', 'HARD');
INSERT INTO SKILL VALUES
(5, 'Comunicação', 'Comunicação clara em times de tecnologia', 'INTERMEDIARIO', 'COMPORTAMENTAL', DATE ''2025-03-05'', 'SOFT');
INSERT INTO SKILL VALUES
(6, 'Trabalho em Equipe', 'Colaboração e trabalho em squads ágeis', 'INTERMEDIARIO', 'COMPORTAMENTAL', DATE ''2025-03-06'', 'SOFT');
INSERT INTO SKILL VALUES
(7, 'Python para Dados', 'Uso de Python em análise exploratória de dados', 'INICIANTE', 'TECNICA', DATE ''2025-03-07'', 'HARD');
INSERT INTO SKILL VALUES
(8, 'Power BI', 'Criação de dashboards e relatórios interativos', 'INICIANTE', 'TECNICA', DATE ''2025-03-08'', 'HARD');
INSERT INTO SKILL VALUES
(9, 'UX Research', 'Fundamentos de pesquisa com usuários', 'INICIANTE', 'TECNICA', DATE ''2025-03-09'', 'HARD');
INSERT INTO SKILL VALUES
(10, 'Metodologias Ágeis', 'Scrum e Kanban aplicados a projetos digitais', 'INTERMEDIARIO', 'TECNICA', DATE ''2025-03-10'', 'HARD');


------------------------------------------------------
-- TRILHAS
------------------------------------------------------
INSERT INTO TRILHA (idTrilha, nome, descricao, nivel, objetivo, status, dtCriacao) VALUES
(1, 'Backend Java Júnior', 'Trilha focada em desenvolvimento backend com Java e Spring', 'INICIANTE', 'Preparar para vagas de backend júnior', 'EM_ANDAMENTO', DATE ''2025-04-01'');
INSERT INTO TRILHA VALUES
(2, 'Iniciação em Dados', 'Fundamentos de análise e visualização de dados', 'INICIANTE', 'Dar primeiros passos em carreira de dados', 'EM_ANDAMENTO', DATE ''2025-04-02'');
INSERT INTO TRILHA VALUES
(3, 'Trilha Fullstack Dev Júnior', 'Fundamentos de frontend e backend', 'INICIANTE', 'Formar base para vagas fullstack júnior', 'NAO_INICIADA', DATE ''2025-04-03'');
INSERT INTO TRILHA VALUES
(4, 'Trilha UX & Produto', 'Bases de experiência do usuário e gestão de produto', 'INICIANTE', 'Apoiar migração para UX/Product', 'NAO_INICIADA', DATE ''2025-04-04'');
INSERT INTO TRILHA VALUES
(5, 'Cloud & DevOps Essentials', 'Fundamentos de cloud e automação de pipelines', 'INICIANTE', 'Introduzir conceitos de DevOps', 'NAO_INICIADA', DATE ''2025-04-05'');
INSERT INTO TRILHA VALUES
(6, 'Cibersegurança para Iniciantes', 'Visão geral de riscos, ataques e defesas', 'INICIANTE', 'Explorar oportunidades em segurança', 'NAO_INICIADA', DATE ''2025-04-06'');
INSERT INTO TRILHA VALUES
(7, 'Finanças Digitais & Open Banking', 'Contexto de bancos digitais e APIs financeiras', 'INICIANTE', 'Aproximar profissionais de finanças tech', 'NAO_INICIADA', DATE ''2025-04-07'');
INSERT INTO TRILHA VALUES
(8, 'Saúde Digital & Telemedicina', 'Bases de soluções digitais na saúde', 'INICIANTE', 'Explorar transição para healthtechs', 'NAO_INICIADA', DATE ''2025-04-08'');
INSERT INTO TRILHA VALUES
(9, 'Educação Digital & Edtechs', 'Criação de produtos educacionais digitais', 'INICIANTE', 'Preparar profissionais para edtechs', 'NAO_INICIADA', DATE ''2025-04-09'');
INSERT INTO TRILHA VALUES
(10, 'Sustentabilidade & ESG nas Org.', 'Projetos de impacto com tecnologia', 'INICIANTE', 'Conectar carreira a impacto socioambiental', 'NAO_INICIADA', DATE ''2025-04-10'');


------------------------------------------------------
-- CURSOS RECOMENDADOS
------------------------------------------------------
INSERT INTO CURSO_RECOMENDADO (idCurso, nome, url, TRILHA_idTrilha, plataforma, duracaoHoras) VALUES
(1, 'Java Fundamentos para Iniciantes', 'https://alura.com/java-fundamentos', 1, 'ALURA', 20);
INSERT INTO CURSO_RECOMENDADO VALUES
(2, 'Spring Boot REST API', 'https://udemy.com/spring-boot-rest', 1, 'UDEMY', 18);
INSERT INTO CURSO_RECOMENDADO VALUES
(3, 'SQL do Zero ao Avançado', 'https://coursera.org/sql', 2, 'COURSERA', 25);
INSERT INTO CURSO_RECOMENDADO VALUES
(4, 'Introdução à Análise de Dados com Python', 'https://youtube.com/python-dados', 2, 'YOUTUBE', 10);
INSERT INTO CURSO_RECOMENDADO VALUES
(5, 'Fundamentos de UX Design', 'https://alura.com/ux-fundamentos', 4, 'ALURA', 16);
INSERT INTO CURSO_RECOMENDADO VALUES
(6, 'Git e GitHub na Prática', 'https://dio.me/git-github', 3, 'DIO', 8);
INSERT INTO CURSO_RECOMENDADO VALUES
(7, 'Introdução à Cloud com AWS', 'https://udemy.com/aws-cloud', 5, 'UDEMY', 15);
INSERT INTO CURSO_RECOMENDADO VALUES
(8, 'Cibersegurança para Iniciantes', 'https://alura.com/ciberseguranca', 6, 'ALURA', 12);
INSERT INTO CURSO_RECOMENDADO VALUES
(9, 'Power BI para Negócios', 'https://fiap.com/powerbi', 2, 'FIAP_ON', 14);
INSERT INTO CURSO_RECOMENDADO VALUES
(10, 'Metodologias Ágeis na Prática', 'https://coursera.org/agile', 3, 'COURSERA', 10);


------------------------------------------------------
-- RELACIONAMENTOS: USUÁRIO x ÁREA
------------------------------------------------------
INSERT INTO USUARIO_AREA (USUARIO_idUsuario, AREA_idArea) VALUES (1, 1);
INSERT INTO USUARIO_AREA VALUES (1, 2);
INSERT INTO USUARIO_AREA VALUES (2, 1);
INSERT INTO USUARIO_AREA VALUES (3, 2);
INSERT INTO USUARIO_AREA VALUES (4, 4);
INSERT INTO USUARIO_AREA VALUES (5, 2);
INSERT INTO USUARIO_AREA VALUES (6, 5);
INSERT INTO USUARIO_AREA VALUES (7, 3);
INSERT INTO USUARIO_AREA VALUES (8, 1);
INSERT INTO USUARIO_AREA VALUES (9, 9);
INSERT INTO USUARIO_AREA VALUES (10, 7);


------------------------------------------------------
-- RELACIONAMENTOS: USUÁRIO x SKILL (skills já possuídas)
------------------------------------------------------
INSERT INTO USUARIO_SKILL (USUARIO_idUsuario, SKILL_idSkill, nivel) VALUES
(1, 1, 'INICIANTE');
INSERT INTO USUARIO_SKILL VALUES (1, 4, 'INICIANTE');
INSERT INTO USUARIO_SKILL VALUES (2, 3, 'INTERMEDIARIO');
INSERT INTO USUARIO_SKILL VALUES (3, 5, 'INTERMEDIARIO');
INSERT INTO USUARIO_SKILL VALUES (4, 1, 'INICIANTE');
INSERT INTO USUARIO_SKILL VALUES (4, 10, 'INICIANTE');
INSERT INTO USUARIO_SKILL VALUES (5, 7, 'INICIANTE');
INSERT INTO USUARIO_SKILL VALUES (6, 8, 'INICIANTE');
INSERT INTO USUARIO_SKILL VALUES (7, 5, 'INTERMEDIARIO');
INSERT INTO USUARIO_SKILL VALUES (8, 2, 'INTERMEDIARIO');
INSERT INTO USUARIO_SKILL VALUES (9, 3, 'INTERMEDIARIO');
INSERT INTO USUARIO_SKILL VALUES (10, 4, 'INICIANTE');


------------------------------------------------------
-- RELACIONAMENTOS: USUÁRIO x TRILHA
------------------------------------------------------
INSERT INTO USUARIO_TRILHA (USUARIO_idUsuario, TRILHA_idTrilha, status, dataInicio, dataConclusao) VALUES
(1, 1, 'EM_ANDAMENTO', DATE ''2025-05-01'', NULL);
INSERT INTO USUARIO_TRILHA VALUES
(2, 2, 'EM_ANDAMENTO', DATE ''2025-05-02'', NULL);
INSERT INTO USUARIO_TRILHA VALUES
(3, 4, 'NAO_INICIADA', NULL, NULL);
INSERT INTO USUARIO_TRILHA VALUES
(4, 1, 'NAO_INICIADA', NULL, NULL);
INSERT INTO USUARIO_TRILHA VALUES
(5, 2, 'EM_ANDAMENTO', DATE ''2025-05-05'', NULL);
INSERT INTO USUARIO_TRILHA VALUES
(6, 5, 'NAO_INICIADA', NULL, NULL);
INSERT INTO USUARIO_TRILHA VALUES
(7, 3, 'NAO_INICIADA', NULL, NULL);
INSERT INTO USUARIO_TRILHA VALUES
(8, 2, 'CONCLUIDA', DATE ''2025-04-01'', DATE ''2025-05-01'');
INSERT INTO USUARIO_TRILHA VALUES
(9, 9, 'NAO_INICIADA', NULL, NULL);
INSERT INTO USUARIO_TRILHA VALUES
(10, 7, 'NAO_INICIADA', NULL, NULL);


------------------------------------------------------
-- RELACIONAMENTOS: TRILHA x SKILL NECESSÁRIA
------------------------------------------------------
INSERT INTO TRILHA_SKILL_NECESSARIA (TRILHA_idTrilha, SKILL_idSkill) VALUES
(1, 1);   -- Backend Java Júnior exige Java
INSERT INTO TRILHA_SKILL_NECESSARIA VALUES
(1, 2);   -- e Spring Boot
INSERT INTO TRILHA_SKILL_NECESSARIA VALUES
(1, 3);   -- e SQL
INSERT INTO TRILHA_SKILL_NECESSARIA VALUES
(2, 3);   -- Iniciação em Dados exige SQL
INSERT INTO TRILHA_SKILL_NECESSARIA VALUES
(2, 7);   -- e Python para Dados
INSERT INTO TRILHA_SKILL_NECESSARIA VALUES
(2, 8);   -- e Power BI
INSERT INTO TRILHA_SKILL_NECESSARIA VALUES
(3, 1);   -- Fullstack usa Java
INSERT INTO TRILHA_SKILL_NECESSARIA VALUES
(3, 4);   -- Git
INSERT INTO TRILHA_SKILL_NECESSARIA VALUES
(3, 10);  -- Metodologias Ágeis
INSERT INTO TRILHA_SKILL_NECESSARIA VALUES
(4, 9);   -- Trilha UX usa UX Research
