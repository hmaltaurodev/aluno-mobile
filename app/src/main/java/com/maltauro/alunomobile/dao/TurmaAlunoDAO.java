package com.maltauro.alunomobile.dao;

import android.util.Log;
import com.maltauro.alunomobile.models.TurmaAluno;
import java.util.ArrayList;
import java.util.List;

public class TurmaAlunoDAO {

    public static long salvar(TurmaAluno turmaAluno) {
        try {
            return turmaAluno.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar o aluno da turma: " + ex.getMessage());
            return -1;
        }
    }

    public static TurmaAluno getTurmaAluno(int id) {
        try {
            return TurmaAluno.findById(TurmaAluno.class, id);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar o aluno da turma: " + ex.getMessage());
            return null;
        }
    }

    public static List<TurmaAluno> getListTurmaAlunos(String where, String[] whererArgs, String orderBy) {
        List<TurmaAluno> turmasAlunos = new ArrayList<>();

        try {
            turmasAlunos = TurmaAluno.find(TurmaAluno.class, where, whererArgs, null, orderBy, null);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de alunos da turma: " + ex.getMessage());
        }

        return turmasAlunos;
    }
}
