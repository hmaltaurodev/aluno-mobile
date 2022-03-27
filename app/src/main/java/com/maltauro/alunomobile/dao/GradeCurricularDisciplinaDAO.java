package com.maltauro.alunomobile.dao;

import android.util.Log;
import com.maltauro.alunomobile.models.GradeCurricularDisciplina;

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
}
