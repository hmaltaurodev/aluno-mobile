package com.maltauro.alunomobile.dao;

import android.util.Log;
import com.maltauro.alunomobile.models.Nota;
import java.util.List;

public class NotaDAO {

    private static final String SELECT_NOTA_EXISTENTE =
            "SELECT * " +
            "FROM NOTA " +
            "WHERE TURMA_ALUNO = ? " +
            "  AND DISCIPLINA = ? " +
            "  AND BIMESTRE = ? ";

    private static final String SELECT_NOTAS_ALUNO_DISCIPLINA = "" +
            "SELECT NOTA.* " +
            "FROM NOTA " +
            "WHERE NOTA.TURMA_ALUNO = ? " +
            "  AND NOTA.DISCIPLINA = ? " +
            "ORDER BY NOTA.BIMESTRE ASC ";

    public static long salvar(Nota nota) {
        try {
            return nota.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar a nota: " + ex.getMessage());
            return -1;
        }
    }

    public static Nota getNotaExistente(long idTurmaAluno, long idDisciplina, int bimestre) {
        try {
            return Nota.findWithQuery(Nota.class, SELECT_NOTA_EXISTENTE, String.valueOf(idTurmaAluno), String.valueOf(idDisciplina), String.valueOf(bimestre)).get(0);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de notas: " + ex.getMessage());
            return null;
        }
    }

    public static List<Nota> getNotasAlunoDisciplina(long idTurmaAluno, long idDisciplina) {
        try {
            return Nota.findWithQuery(Nota.class, SELECT_NOTAS_ALUNO_DISCIPLINA, String.valueOf(idTurmaAluno), String.valueOf(idDisciplina));
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de notas: " + ex.getMessage());
            return null;
        }
    }
}
