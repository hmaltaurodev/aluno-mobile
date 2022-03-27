package com.maltauro.alunomobile.dao;

import android.util.Log;
import com.maltauro.alunomobile.models.GradeCurricular;
import java.util.ArrayList;
import java.util.List;

public class GradeCurricularDAO {

    private static final String SELECT_GRADE_CURRICULAR_EXISTENTE =
            "SELECT * " +
            "FROM GRADE_CURRICULAR " +
            "WHERE CURSO = ? " +
            "  AND ANO_ACADEMICO = ? " +
            "  AND REGIME_ACADEMICO = ? " +
            "  AND SEMESTRE_PERIODO = ? ";

    private static final String SELECT_GRADE_CURRICULAR_TURMA =
            "SELECT GRADE_CURRICULAR.* " +
            "FROM GRADE_CURRICULAR " +
            "INNER JOIN TURMA ON TURMA.GRADE_CURRICULAR = GRADE_CURRICULAR.ID " +
            "WHERE TURMA.ID = ? ";

    public static long salvar(GradeCurricular gradeCurricular) {
        try {
            return gradeCurricular.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar a grade curricular: " + ex.getMessage());
            return -1;
        }
    }

    public static GradeCurricular getGradeCurricularExistente(long idCurso, int anoAcademico, int regimeAcademico, int semestrePeriodo) {
        try {
            return GradeCurricular.findWithQuery(GradeCurricular.class, SELECT_GRADE_CURRICULAR_EXISTENTE, String.valueOf(idCurso), String.valueOf(anoAcademico), String.valueOf(regimeAcademico), String.valueOf(semestrePeriodo)).get(0);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a grade curricular: " + ex.getMessage());
            return null;
        }
    }

    public static GradeCurricular getGradeCurricularTurma(long idTurma) {
        try {
            return GradeCurricular.findWithQuery(GradeCurricular.class, SELECT_GRADE_CURRICULAR_TURMA, String.valueOf(idTurma)).get(0);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a grade curricular da turma: " + ex.getMessage());
            return null;
        }
    }

    public static List<GradeCurricular> getListGradesCurriculares(String where, String[] whererArgs, String orderBy) {
        List<GradeCurricular> gradesCurriculares = new ArrayList<>();

        try {
            gradesCurriculares = GradeCurricular.find(GradeCurricular.class, where, whererArgs, null, orderBy, null);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de grades curriculares: " + ex.getMessage());
        }

        return gradesCurriculares;
    }
}
