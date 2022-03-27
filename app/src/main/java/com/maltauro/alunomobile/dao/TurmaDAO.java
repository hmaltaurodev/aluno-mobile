package com.maltauro.alunomobile.dao;

import android.util.Log;
import com.maltauro.alunomobile.models.Turma;
import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {

    private static final String SELECT_TURMAS_CURSO = "" +
            "SELECT TURMA.* " +
            "FROM TURMA " +
            "INNER JOIN GRADE_CURRICULAR ON TURMA.GRADE_CURRICULAR = GRADE_CURRICULAR.ID " +
            "WHERE GRADE_CURRICULAR.CURSO = ? ";

    private static final String SELECT_TURMA_EXISTENTE = "" +
            "SELECT * " +
            "FROM TURMA " +
            "WHERE GRADE_CURRICULAR = ? " +
            "  AND ANO_PERIODO = ? ";

    public static long salvar(Turma turma) {
        try {
            return turma.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar a turma: " + ex.getMessage());
            return -1;
        }
    }

    public static List<Turma> getListTurmas(String where, String[] whererArgs, String orderBy) {
        List<Turma> turmas = new ArrayList<>();

        try {
            turmas = Turma.find(Turma.class, where, whererArgs, null, orderBy, null);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de turmas: " + ex.getMessage());
        }

        return turmas;
    }

    public static Turma getTurmaExistente(long idGradeCurricular, int anoPeriodo) {
        try {
            return Turma.findWithQuery(Turma.class, SELECT_TURMA_EXISTENTE, String.valueOf(idGradeCurricular), String.valueOf(anoPeriodo)).get(0);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a turma: " + ex.getMessage());
            return null;
        }
    }

    public static List<Turma> getListTurmasCurso(long idCurso) {
        List<Turma> turmas = new ArrayList<>();

        try {
            turmas = Turma.findWithQuery(Turma.class, SELECT_TURMAS_CURSO, String.valueOf(idCurso));
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de turmas do curso: " + ex.getMessage());
        }

        return turmas;
    }
}
