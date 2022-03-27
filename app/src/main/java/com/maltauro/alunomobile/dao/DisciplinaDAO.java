package com.maltauro.alunomobile.dao;

import android.util.Log;
import com.maltauro.alunomobile.models.Disciplina;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    private static final String SELECT_DISCIPLINAS_DA_GRADE_CURRICULAR = "" +
            "SELECT DISCIPLINA.* " +
            "FROM DISCIPLINA " +
            "INNER JOIN GRADE_CURRICULAR_DISCIPLINA ON GRADE_CURRICULAR_DISCIPLINA.DISCIPLINA = DISCIPLINA.ID " +
            "INNER JOIN GRADE_CURRICULAR ON GRADE_CURRICULAR_DISCIPLINA.GRADE_CURRICULAR = GRADE_CURRICULAR.ID " +
            "INNER JOIN TURMA ON TURMA.GRADE_CURRICULAR = GRADE_CURRICULAR.ID " +
            "WHERE TURMA.ID = ? ";

    public static long salvar(Disciplina disciplina) {
        try {
            return disciplina.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar a disciplina: " + ex.getMessage());
            return -1;
        }
    }

    public static Disciplina getDisciplina(long id) {
        try {
            return Disciplina.findById(Disciplina.class, id);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a disciplina: " + ex.getMessage());
            return null;
        }
    }

    public static List<Disciplina> getListDisciplinas(String where, String[] whererArgs, String orderBy) {
        List<Disciplina> disciplinas = new ArrayList<>();

        try {
            disciplinas = Disciplina.find(Disciplina.class, where, whererArgs, null, orderBy, null);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de disciplinas: " + ex.getMessage());
        }

        return disciplinas;
    }

    public static List<Disciplina> getListDisciplinasGradeCurricular(long idTurma) {
        List<Disciplina> disciplinas = new ArrayList<>();

        try {
            disciplinas = Disciplina.findWithQuery(Disciplina.class, SELECT_DISCIPLINAS_DA_GRADE_CURRICULAR, String.valueOf(idTurma));
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de disciplinas da grade curricular: " + ex.getMessage());
        }

        return disciplinas;
    }
}
