/* =====================================================================
   03 — FUNÇÕES DO PROJETO EQUALPATH
   ===================================================================== */

------------------------------------------------------------------------
-- 01) TOTAL DE HORAS DE UMA TRILHA
------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION FN_TOTAL_HORAS_TRILHA (
    p_idTrilha IN NUMBER
) RETURN NUMBER AS
    v_total NUMBER := 0;
BEGIN
    SELECT NVL(SUM(duracaoHoras), 0)
      INTO v_total
      FROM CURSO_RECOMENDADO
     WHERE TRILHA_idTrilha = p_idTrilha;

    RETURN v_total;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 0; -- fallback corporativo
END;
/
------------------------------------------------------------------------
-- 02) QUANTIDADE DE SKILLS DE UM USUÁRIO
------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION FN_QTD_SKILLS_USUARIO (
    p_idUsuario IN NUMBER
) RETURN NUMBER AS
    v_qtd NUMBER := 0;
BEGIN
    SELECT COUNT(*)
      INTO v_qtd
      FROM USUARIO_SKILL
     WHERE USUARIO_idUsuario = p_idUsuario;

    RETURN v_qtd;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 0;
END;
/
------------------------------------------------------------------------
-- 03) PERFIL COMPLETO (usuário está 100% configurado)
------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION FN_PERFIL_COMPLETO (
    p_idUsuario IN NUMBER
) RETURN VARCHAR2 AS
    v_area   NUMBER := 0;
    v_trilha NUMBER := 0;
    v_skill  NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_area
      FROM USUARIO_AREA
     WHERE USUARIO_idUsuario = p_idUsuario;

    SELECT COUNT(*) INTO v_trilha
      FROM USUARIO_TRILHA
     WHERE USUARIO_idUsuario = p_idUsuario;

    SELECT COUNT(*) INTO v_skill
      FROM USUARIO_SKILL
     WHERE USUARIO_idUsuario = p_idUsuario;

    IF v_area > 0 AND v_trilha > 0 AND v_skill > 0 THEN
        RETURN 'SIM';
    ELSE
        RETURN 'NAO';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 'NAO';
END;
/
