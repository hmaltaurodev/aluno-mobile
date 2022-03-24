package com.maltauro.alunomobile.dao;

import android.util.Log;

import com.maltauro.alunomobile.models.Curso;
import com.maltauro.alunomobile.models.GradeCurricular;
import java.util.ArrayList;
import java.util.List;

public class GradeCurricularDAO {

    private static final String SELECT_GRADE_CURRICULAR_EXISTENTE =
            "SELECT " +
            "   ID, " +
            "   CURSO, " +
            "   ANO_ACADEMICO, " +
            "   REGIME_ACADEMICO, " +
            "   SEMESTRE_PERIODO " +
            "FROM GRADE_CURRICULAR " +
            "WHERE CURSO = ? " +
            "  AND ANO_ACADEMICO = ? " +
            "  AND REGIME_ACADEMICO = ? " +
            "  AND SEMESTRE_PERIODO = ? ";

    public static long salvar(GradeCurricular gradeCurricular) {
        try {
            return gradeCurricular.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar a grade curricular: " + ex.getMessage());
            return -1;
        }
    }

    public static GradeCurricular getGradeCurricular(int id) {
        try {
            return GradeCurricular.findById(GradeCurricular.class, id);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a grade curricular: " + ex.getMessage());
            return null;
        }
    }

    public static GradeCurricular getGradeCurricular(String... arguments) {
        try {
            return GradeCurricular.findWithQuery(GradeCurricular.class, SELECT_GRADE_CURRICULAR_EXISTENTE, arguments).get(0);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a grade curricular: " + ex.getMessage());
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

    public static boolean delete(GradeCurricular gradeCurricular) {
        try {
            return GradeCurricular.delete(gradeCurricular);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao deletar a grade curricular: " + ex.getMessage());
            return false;
        }
    }
}
