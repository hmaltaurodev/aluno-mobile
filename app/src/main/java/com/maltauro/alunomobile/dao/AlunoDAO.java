package com.maltauro.alunomobile.dao;

import android.util.Log;
import com.maltauro.alunomobile.models.Aluno;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private static final String SELECT_ALUNOS_SEM_TURMA =
            "SELECT " +
            "   ALUNO.ID, " +
            "   ALUNO.RA, " +
            "   ALUNO.NOME, " +
            "   ALUNO.CPF, " +
            "   ALUNO.DATA_NASCIMENTO, " +
            "   ALUNO.DATA_MATRICULA " +
            "FROM ALUNO " +
            "LEFT JOIN TURMA_ALUNO ON ALUNO.ID = TURMA_ALUNO.ALUNO " +
            "WHERE TURMA_ALUNO.ID IS NULL ";

    public static long salvar(Aluno aluno) {
        try {
            return aluno.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar o aluno: " + ex.getMessage());
            return -1;
        }
    }

    public static Aluno getAluno(int id) {
        try {
            return Aluno.findById(Aluno.class, id);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar o aluno: " + ex.getMessage());
            return null;
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

    public static boolean delete(Aluno aluno) {
        try {
            return Aluno.delete(aluno);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao deletar o aluno: " + ex.getMessage());
            return false;
        }
    }
}
