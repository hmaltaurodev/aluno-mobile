package com.maltauro.alunomobile.dao;

import android.util.Log;
import com.maltauro.alunomobile.models.GradeCurricularDisciplina;
import java.util.ArrayList;
import java.util.List;

public class GradeCurricularDisciplinaDAO {

    public static long salvar(GradeCurricularDisciplina gradeCurricularDisciplina) {
        try {
            return gradeCurricularDisciplina.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar a disciplina da grade curricular: " + ex.getMessage());
            return -1;
        }
    }

    public static GradeCurricularDisciplina getGradeCurricularDisciplina(int id) {
        try {
            return GradeCurricularDisciplina.findById(GradeCurricularDisciplina.class, id);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar as disciplinas da grade curricular: " + ex.getMessage());
            return null;
        }
    }

    public static List<GradeCurricularDisciplina> getListGradesCurricularesDisciplinas(String where, String[] whererArgs, String orderBy) {
        List<GradeCurricularDisciplina> gradesCurriculares = new ArrayList<>();

        try {
            gradesCurriculares = GradeCurricularDisciplina.find(GradeCurricularDisciplina.class, where, whererArgs, null, orderBy, null);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista das disciplinas da grade curriculare: " + ex.getMessage());
        }

        return gradesCurriculares;
    }
}
