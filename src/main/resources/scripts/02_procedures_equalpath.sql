/* =====================================================================
   PROCEDURES DE INSERT
   ===================================================================== */

-- USUARIO
CREATE OR REPLACE PROCEDURE PRC_INS_USUARIO (
    p_idUsuario        IN NUMBER   DEFAULT NULL,
    p_nome             IN VARCHAR2,
    p_sobrenome        IN VARCHAR2,
    p_email            IN VARCHAR2,
    p_telefone         IN VARCHAR2,
    p_dtCadastro       IN DATE,
    p_estado           IN VARCHAR2,
    p_objetivoCarreira IN VARCHAR2,
    p_statusPerfil     IN VARCHAR2
) AS
    v_id     NUMBER;
    v_estado VARCHAR2(2);
BEGIN
    v_id     := NVL(p_idUsuario, SEQ_USUARIO.NEXTVAL);
    v_estado := SUBSTR(TRIM(p_estado), 1, 2);  -- garante 2 caracteres

    INSERT INTO USUARIO (
        idUsuario, nome, sobrenome, email, telefone,
        dtCadastro, estado, objetivoCarreira, statusPerfil
    ) VALUES (
        v_id, p_nome, p_sobrenome, p_email, p_telefone,
        p_dtCadastro, v_estado, p_objetivoCarreira, p_statusPerfil
    );

EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        RAISE_APPLICATION_ERROR(-20001, 'Usuário ou e-mail já cadastrado: ' || p_email);
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20002, 'Erro ao inserir usuário: ' || SQLERRM);
END;
/
 
-- AREA
CREATE OR REPLACE PROCEDURE PRC_INS_AREA (
    p_idArea    IN NUMBER   DEFAULT NULL,
    p_nome      IN VARCHAR2,
    p_descricao IN VARCHAR2,
    p_dtCriacao IN DATE
) AS
    v_id NUMBER;
BEGIN
    v_id := NVL(p_idArea, SEQ_AREA.NEXTVAL);

    INSERT INTO AREA (idArea, nome, descricao, dtCriacao)
    VALUES (v_id, p_nome, p_descricao, p_dtCriacao);

EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        RAISE_APPLICATION_ERROR(-20003, 'Área já cadastrada: ' || p_nome);
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20004, 'Erro ao inserir área: ' || SQLERRM);
END;
/
 
-- SKILL
CREATE OR REPLACE PROCEDURE PRC_INS_SKILL (
    p_idSkill      IN NUMBER   DEFAULT NULL,
    p_nome         IN VARCHAR2,
    p_descricao    IN VARCHAR2,
    p_nivel        IN VARCHAR2,
    p_categoria    IN VARCHAR2,
    p_ultimoAcesso IN DATE,
    p_tipo         IN VARCHAR2
) AS
    v_id NUMBER;
BEGIN
    v_id := NVL(p_idSkill, SEQ_SKILL.NEXTVAL);

    INSERT INTO SKILL (
        idSkill, nome, descricao, nivel, categoria,
        ultimoAcesso, tipo
    ) VALUES (
        v_id, p_nome, p_descricao, p_nivel, p_categoria,
        p_ultimoAcesso, p_tipo
    );

EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        RAISE_APPLICATION_ERROR(-20005, 'Skill já cadastrada: ' || p_nome);
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20006, 'Erro ao inserir skill: ' || SQLERRM);
END;
/

-- TRILHA
CREATE OR REPLACE PROCEDURE PRC_INS_TRILHA (
    p_idTrilha  IN NUMBER   DEFAULT NULL,
    p_nome      IN VARCHAR2,
    p_descricao IN VARCHAR2,
    p_nivel     IN VARCHAR2,
    p_objetivo  IN VARCHAR2,
    p_status    IN VARCHAR2,
    p_dtCriacao IN DATE
) AS
    v_id NUMBER;
BEGIN
    v_id := NVL(p_idTrilha, SEQ_TRILHA.NEXTVAL);

    INSERT INTO TRILHA (
        idTrilha, nome, descricao, nivel, objetivo,
        status, dtCriacao
    ) VALUES (
        v_id, p_nome, p_descricao, p_nivel, p_objetivo,
        p_status, p_dtCriacao
    );

EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        RAISE_APPLICATION_ERROR(-20007, 'Trilha já cadastrada: ' || p_nome);
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20008, 'Erro ao inserir trilha: ' || SQLERRM);
END;
/

-- CURSO_RECOMENDADO
CREATE OR REPLACE PROCEDURE PRC_INS_CURSO_RECOMENDADO (
    p_idCurso         IN NUMBER   DEFAULT NULL,
    p_nome            IN VARCHAR2,
    p_url             IN VARCHAR2,
    p_TRILHA_idTrilha IN NUMBER,
    p_plataforma      IN VARCHAR2,
    p_duracaoHoras    IN NUMBER
) AS
    v_id NUMBER;
BEGIN
    v_id := NVL(p_idCurso, SEQ_CURSO.NEXTVAL);

    INSERT INTO CURSO_RECOMENDADO (
        idCurso, nome, url, TRILHA_idTrilha,
        plataforma, duracaoHoras
    ) VALUES (
        v_id, p_nome, p_url, p_TRILHA_idTrilha,
        p_plataforma, p_duracaoHoras
    );

EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20009, 'Erro ao inserir curso recomendado: ' || SQLERRM);
END;
/

-- USUARIO_AREA
CREATE OR REPLACE PROCEDURE PRC_INS_USUARIO_AREA (
    p_idUsuario IN NUMBER,
    p_idArea    IN NUMBER
) AS
BEGIN
    INSERT INTO USUARIO_AREA (USUARIO_idUsuario, AREA_idArea)
    VALUES (p_idUsuario, p_idArea);

EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20010, 'Erro ao vincular usuário à área: ' || SQLERRM);
END;
/

-- USUARIO_TRILHA
CREATE OR REPLACE PROCEDURE PRC_INS_USUARIO_TRILHA (
    p_idUsuario IN NUMBER,
    p_idTrilha  IN NUMBER
) AS
BEGIN
    INSERT INTO USUARIO_TRILHA (USUARIO_idUsuario, TRILHA_idTrilha)
    VALUES (p_idUsuario, p_idTrilha);

EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20011, 'Erro ao vincular usuário à trilha: ' || SQLERRM);
END;
/

-- USUARIO_SKILL
CREATE OR REPLACE PROCEDURE PRC_INS_USUARIO_SKILL (
    p_idUsuario IN NUMBER,
    p_idSkill   IN NUMBER
) AS
BEGIN
    INSERT INTO USUARIO_SKILL (USUARIO_idUsuario, SKILL_idSkill)
    VALUES (p_idUsuario, p_idSkill);

EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20012, 'Erro ao vincular usuário à skill: ' || SQLERRM);
END;
/

-- TRILHA_SKILL_NECESSARIA
CREATE OR REPLACE PROCEDURE PRC_INS_TRILHA_SKILL_NEC (
    p_idTrilha IN NUMBER,
    p_idSkill  IN NUMBER
) AS
BEGIN
    INSERT INTO TRILHA_SKILL_NECESSARIA (TRILHA_idTrilha, SKILL_idSkill)
    VALUES (p_idTrilha, p_idSkill);

EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20013, 'Erro ao vincular skill necessária à trilha: ' || SQLERRM);
END;
/
 
/* =====================================================================
   CARGA INICIAL — CONSUMINDO SEQUENCES (IDS = NULL)
   ===================================================================== */

BEGIN
    /* ===================== USUARIO ===================== */
    PRC_INS_USUARIO(NULL,'Alane','Rocha','alane.rocha@equalpath.com','11911110001',SYSDATE,'SP','CRESCER_CARREIRA','ATIVO');
    PRC_INS_USUARIO(NULL,'Anna','Bonfim','anna.bonfim@equalpath.com','11911110002',SYSDATE,'SP','CRESCER_CARREIRA','ATIVO');
    PRC_INS_USUARIO(NULL,'Maria','Penas','maria.penas@equalpath.com','11911110003',SYSDATE,'SP','CRESCER_CARREIRA','PENDENTE');
    PRC_INS_USUARIO(NULL,'Lucas','Oliveira','lucas.oliveira@equalpath.com','21922220004',SYSDATE,'RJ','CRESCER_CARREIRA','ATIVO');
    PRC_INS_USUARIO(NULL,'Karina','Santos','karina.santos@equalpath.com','31933330005',SYSDATE,'MG','CRESCER_CARREIRA','ATIVO');
    PRC_INS_USUARIO(NULL,'Vanilton','Lima','vanilton.lima@equalpath.com','71944440006',SYSDATE,'BA','MIGRAR_AREA','INATIVO');
    PRC_INS_USUARIO(NULL,'Joao','Pereira','joao.pereira@equalpath.com','41955550007',SYSDATE,'PR','MIGRAR_AREA','ATIVO');
    PRC_INS_USUARIO(NULL,'Bruna','Costa','bruna.costa@equalpath.com','61966660008',SYSDATE,'DF','MIGRAR_AREA','ATIVO');
    PRC_INS_USUARIO(NULL,'Rafael','Souza','rafael.souza@equalpath.com','81977770009',SYSDATE,'PE','RECOLOCACAO','PENDENTE');
    PRC_INS_USUARIO(NULL,'Paula','Ferreira','paula.ferreira@equalpath.com','11988880010',SYSDATE,'SP','PRIMEIRO_EMPREGO','ATIVO');

    /* ===================== AREA ===================== */
    PRC_INS_AREA(NULL,'Backend','Desenvolvimento de APIs e serviços',SYSDATE);
    PRC_INS_AREA(NULL,'Dados','Engenharia e análise de dados',SYSDATE);
    PRC_INS_AREA(NULL,'Frontend','Interfaces web e experiência do usuário',SYSDATE);
    PRC_INS_AREA(NULL,'DevOps','CI/CD, automação e infraestrutura como código',SYSDATE);
    PRC_INS_AREA(NULL,'Mobile','Aplicativos Android e iOS',SYSDATE);
    PRC_INS_AREA(NULL,'UX/UI','Pesquisa com usuário e design',SYSDATE);
    PRC_INS_AREA(NULL,'Cloud','Arquiteturas em nuvem',SYSDATE);
    PRC_INS_AREA(NULL,'Seguranca','Segurança da informação',SYSDATE);
    PRC_INS_AREA(NULL,'Produto','Gestão de produto digital',SYSDATE);
    PRC_INS_AREA(NULL,'QA','Qualidade e automação de testes',SYSDATE);

    /* ===================== SKILL ===================== */
    PRC_INS_SKILL(NULL,'Java','Linguagem OO para backend','INTERMEDIARIO','BACKEND',SYSDATE-1,'TECNICA');
    PRC_INS_SKILL(NULL,'Spring Boot','APIs REST em Java','INTERMEDIARIO','BACKEND',SYSDATE-2,'TECNICA');
    PRC_INS_SKILL(NULL,'SQL','Consultas relacionais','INTERMEDIARIO','DADOS',SYSDATE-3,'TECNICA');
    PRC_INS_SKILL(NULL,'PL/SQL','Procedures e funções Oracle','BASICO','DADOS',SYSDATE-4,'TECNICA');
    PRC_INS_SKILL(NULL,'C#','Backend .NET','BASICO','BACKEND',SYSDATE-5,'TECNICA');
    PRC_INS_SKILL(NULL,'.NET','APIs .NET','BASICO','BACKEND',SYSDATE-6,'TECNICA');
    PRC_INS_SKILL(NULL,'React','SPAs com React','INTERMEDIARIO','FRONTEND',SYSDATE-7,'TECNICA');
    PRC_INS_SKILL(NULL,'Angular','SPA em TypeScript','BASICO','FRONTEND',SYSDATE-8,'TECNICA');
    PRC_INS_SKILL(NULL,'Python','Dados e automação','INTERMEDIARIO','DADOS',SYSDATE-9,'TECNICA');
    PRC_INS_SKILL(NULL,'Power BI','Dashboards','BASICO','DADOS',SYSDATE-10,'TECNICA');

    /* ===================== TRILHA ===================== */
    PRC_INS_TRILHA(NULL,'Backend Java Jr','APIs REST com Java','INTERMEDIARIO','Backend em squads','ATIVA',SYSDATE);
    PRC_INS_TRILHA(NULL,'Data Analytics','Fundamentos de dados','INICIANTE','BI e relatórios','ATIVA',SYSDATE);
    PRC_INS_TRILHA(NULL,'Frontend React','SPAs com React','INTERMEDIARIO','UI moderna','ATIVA',SYSDATE);
    PRC_INS_TRILHA(NULL,'DevOps Essentials','CI/CD','INICIANTE','Pipelines automatizados','ATIVA',SYSDATE);
    PRC_INS_TRILHA(NULL,'Mobile Kotlin','Apps Android','INICIANTE','Publicar apps','ATIVA',SYSDATE);
    PRC_INS_TRILHA(NULL,'UX Research','Pesquisa com usuários','INICIANTE','Experiência de uso','ATIVA',SYSDATE);
    PRC_INS_TRILHA(NULL,'Cloud Practitioner','Fundamentos cloud','INICIANTE','Serviços principais','ATIVA',SYSDATE);
    PRC_INS_TRILHA(NULL,'Cyber Security','Fundamentos de segurança','INICIANTE','Mitigar riscos','ATIVA',SYSDATE);
    PRC_INS_TRILHA(NULL,'QA Automation','Testes automatizados','INTERMEDIARIO','Aumentar cobertura','ATIVA',SYSDATE);
    PRC_INS_TRILHA(NULL,'Product Management','Gestão de produto','INTERMEDIARIO','Discovery e roadmap','ATIVA',SYSDATE);

    /* ===================== CURSO_RECOMENDADO ===================== */
    PRC_INS_CURSO_RECOMENDADO(NULL,'Spring Boot Essentials','https://curso.com/spring-boot',1,'UDEMY',20);
    PRC_INS_CURSO_RECOMENDADO(NULL,'Java OO na Pratica','https://curso.com/java-oo',1,'ALURA',16);
    PRC_INS_CURSO_RECOMENDADO(NULL,'Introducao a Data Analytics','https://curso.com/data-analytics',2,'COURSERA',12);
    PRC_INS_CURSO_RECOMENDADO(NULL,'SQL para Analise de Dados','https://curso.com/sql-dados',2,'ALURA',10);
    PRC_INS_CURSO_RECOMENDADO(NULL,'React do Zero ao Deploy','https://curso.com/react',3,'UDEMY',24);
    PRC_INS_CURSO_RECOMENDADO(NULL,'Pipeline CI/CD com GitHub Actions','https://curso.com/devops-ci-cd',4,'DIO',8);
    PRC_INS_CURSO_RECOMENDADO(NULL,'Kotlin para Android','https://curso.com/kotlin-android',5,'ALURA',18);
    PRC_INS_CURSO_RECOMENDADO(NULL,'UX Research na Pratica','https://curso.com/ux-research',6,'COURSERA',14);
    PRC_INS_CURSO_RECOMENDADO(NULL,'Fundamentos de Cloud','https://curso.com/cloud',7,'DIO',10);
    PRC_INS_CURSO_RECOMENDADO(NULL,'Automacao de Testes com Selenium','https://curso.com/selenium',9,'UDEMY',12);

    /* ===================== USUARIO_AREA ===================== */
    PRC_INS_USUARIO_AREA(1,1);
    PRC_INS_USUARIO_AREA(1,2);
    PRC_INS_USUARIO_AREA(2,1);
    PRC_INS_USUARIO_AREA(3,2);
    PRC_INS_USUARIO_AREA(4,4);
    PRC_INS_USUARIO_AREA(5,1);
    PRC_INS_USUARIO_AREA(6,2);
    PRC_INS_USUARIO_AREA(7,5);
    PRC_INS_USUARIO_AREA(8,6);
    PRC_INS_USUARIO_AREA(9,8);

    /* ===================== USUARIO_TRILHA ===================== */
    PRC_INS_USUARIO_TRILHA(1,1);
    PRC_INS_USUARIO_TRILHA(2,1);
    PRC_INS_USUARIO_TRILHA(3,2);
    PRC_INS_USUARIO_TRILHA(4,4);
    PRC_INS_USUARIO_TRILHA(5,3);
    PRC_INS_USUARIO_TRILHA(6,2);
    PRC_INS_USUARIO_TRILHA(7,5);
    PRC_INS_USUARIO_TRILHA(8,6);
    PRC_INS_USUARIO_TRILHA(9,8);
    PRC_INS_USUARIO_TRILHA(10,9);

    /* ===================== USUARIO_SKILL ===================== */
    PRC_INS_USUARIO_SKILL(1,1);
    PRC_INS_USUARIO_SKILL(1,2);
    PRC_INS_USUARIO_SKILL(2,1);
    PRC_INS_USUARIO_SKILL(3,3);
    PRC_INS_USUARIO_SKILL(4,4);
    PRC_INS_USUARIO_SKILL(5,7);
    PRC_INS_USUARIO_SKILL(6,9);
    PRC_INS_USUARIO_SKILL(7,5);
    PRC_INS_USUARIO_SKILL(8,7);
    PRC_INS_USUARIO_SKILL(9,10);

    /* ===================== TRILHA_SKILL_NECESSARIA ===================== */
    PRC_INS_TRILHA_SKILL_NEC(1,1);
    PRC_INS_TRILHA_SKILL_NEC(1,2);
    PRC_INS_TRILHA_SKILL_NEC(2,3);
    PRC_INS_TRILHA_SKILL_NEC(2,10);
    PRC_INS_TRILHA_SKILL_NEC(3,7);
    PRC_INS_TRILHA_SKILL_NEC(4,4);
    PRC_INS_TRILHA_SKILL_NEC(4,9);
    PRC_INS_TRILHA_SKILL_NEC(5,5);
    PRC_INS_TRILHA_SKILL_NEC(7,9);
    PRC_INS_TRILHA_SKILL_NEC(9,3);
END;
/

COMMIT;
