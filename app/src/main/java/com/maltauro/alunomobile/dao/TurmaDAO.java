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

    public static long salvar(Turma turma) {
        try {
            return turma.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar a turma: " + ex.getMessage());
            return -1;
        }
    }

    public static Turma getTurma(int id) {
        try {
            return Turma.findById(Turma.class, id);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a turma: " + ex.getMessage());
            return null;
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

    public static List<Turma> getListTurmasCurso(String idCurso) {
        List<Turma> turmas = new ArrayList<>();

        try {
            turmas = Turma.findWithQuery(Turma.class, SELECT_TURMAS_CURSO, idCurso);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de turmas do curso: " + ex.getMessage());
        }

        return turmas;
    }
}
