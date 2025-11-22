/* =====================================================================
   04 — TESTES EVIDENCIAIS DO PROJETO EQUALPATH
   ===================================================================== */

SET SERVEROUTPUT ON;

PROMPT ==== TESTE 1: ERRO TRATADO (PRC_INS_USUARIO) ====

DECLARE
BEGIN
    -- Força e-mail duplicado para acionar tratamento da PRC_INS_USUARIO
    PRC_INS_USUARIO(
        NULL,
        'Teste',
        'Duplicado',
        'alane.rocha@equalpath.com',
        '11900000000',
        SYSDATE,
        'SP',
        'Teste',
        'ATIVO'
    );
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Teste 1 - Erro tratado pela procedure: ' || SQLERRM);
END;
/
---------------------------------------------------------

PROMPT ==== TESTE 2: FN_TOTAL_HORAS_TRILHA ====

DECLARE
    v_total NUMBER;
BEGIN
    v_total := FN_TOTAL_HORAS_TRILHA(1);
    DBMS_OUTPUT.PUT_LINE('Teste 2 - Total de horas da trilha 1 = ' || v_total);
END;
/
---------------------------------------------------------

PROMPT ==== TESTE 3: FN_QTD_SKILLS_USUARIO ====

DECLARE
    v_qtd NUMBER;
BEGIN
    v_qtd := FN_QTD_SKILLS_USUARIO(1);
    DBMS_OUTPUT.PUT_LINE('Teste 3 - Quantidade de skills do usuário 1 = ' || v_qtd);
END;
/
---------------------------------------------------------

PROMPT ==== TESTE 4: FN_PERFIL_COMPLETO ====

DECLARE
    v_status VARCHAR2(3);
BEGIN
    v_status := FN_PERFIL_COMPLETO(1);
    DBMS_OUTPUT.PUT_LINE('Teste 4 - Perfil do usuário 1 está completo? ' || v_status);
END;
/
