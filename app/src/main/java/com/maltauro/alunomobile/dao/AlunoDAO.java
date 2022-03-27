package com.maltauro.alunomobile.dao;

import android.util.Log;
import com.maltauro.alunomobile.models.Aluno;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private static final String SELECT_ALUNOS_SEM_TURMA =
            "SELECT ALUNO.* " +
            "FROM ALUNO " +
            "LEFT JOIN TURMA_ALUNO ON ALUNO.ID = TURMA_ALUNO.ALUNO " +
            "WHERE TURMA_ALUNO.ID IS NULL ";

    private static final String SELECT_ALUNOS_DA_TURMA =
            "SELECT ALUNO.* " +
            "FROM ALUNO " +
            "INNER JOIN TURMA_ALUNO ON ALUNO.ID = TURMA_ALUNO.ALUNO " +
            "WHERE TURMA_ALUNO.TURMA = ? ";

    public static long salvar(Aluno aluno) {
        try {
            return aluno.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar o aluno: " + ex.getMessage());
            return -1;
        }
    }

    public static List<Aluno> getListAlunosSemTurma() {
        List<Aluno> alunos = new ArrayList<>();

        try {
            alunos = Aluno.findWithQuery(Aluno.class, SELECT_ALUNOS_SEM_TURMA);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de alunos sem turma: " + ex.getMessage());
        }

        return alunos;
    }

    public static List<Aluno> getListAlunosTurma(String idTurma) {
        List<Aluno> alunos = new ArrayList<>();

        try {
            alunos = Aluno.findWithQuery(Aluno.class, SELECT_ALUNOS_DA_TURMA, idTurma);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de alunos da turma: " + ex.getMessage());
        }

        return alunos;
    }

    public static List<Aluno> getListAlunos(String where, String[] whererArgs, String orderBy) {
        List<Aluno> alunos = new ArrayList<>();

        try {
            alunos = Aluno.find(Aluno.class, where, whererArgs, null, orderBy, null);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de alunos: " + ex.getMessage());
        }

        return alunos;
    }
}
