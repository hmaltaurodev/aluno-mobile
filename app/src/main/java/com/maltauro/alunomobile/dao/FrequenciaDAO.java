package com.maltauro.alunomobile.dao;

import android.util.Log;
import com.maltauro.alunomobile.models.Frequencia;

public class FrequenciaDAO {

    private static final String SELECT_AULAS_MINISTRADAS_TURMA_DISCIPLINA =
            "SELECT " +
            "   MIN(FREQUENCIA.ID) ID," +
            "   MIN(FREQUENCIA.TURMA_ALUNO) TURMA_ALUNO," +
            "   FREQUENCIA.DISCIPLINA," +
            "   FREQUENCIA.NUMERO_AULA," +
            "   MIN(FREQUENCIA.PRESENCA) PRESENCA " +
            "FROM FREQUENCIA " +
            "INNER JOIN TURMA_ALUNO ON TURMA_ALUNO.ID = FREQUENCIA.TURMA_ALUNO " +
            "WHERE TURMA_ALUNO.TURMA = ? " +
            "  AND FREQUENCIA.DISCIPLINA = ? " +
            "GROUP BY FREQUENCIA.DISCIPLINA, FREQUENCIA.NUMERO_AULA ";

    private static final String SELECT_FREQUENCIA_TURMA_DISCIPLINA_ALUNO = "" +
            "SELECT .* " +
            "FROM FREQUENCIA " +
            "WHERE TURMA_ALUNO = ? " +
            "  AND DISCIPLINA = ? " +
            "  AND PRESENCA = 1 ";

    private static final String SELECT_FREQUENCIA_EXISTENTE = "" +
            "SELECT * " +
            "FROM FREQUENCIA " +
            "WHERE TURMA_ALUNO = ? " +
            "  AND DISCIPLINA = ? " +
            "  AND NUMERO_AULA = ? ";

    public static long salvar(Frequencia frequencia) {
        try {
            Log.i("TurmaAluno", String.valueOf(frequencia.getTurmaAluno().getId()));
            Log.i("Aluno", String.valueOf(frequencia.getTurmaAluno().getAluno().getId()));
            Log.i("Presenca", String.valueOf(frequencia.isPresenca()));
            return frequencia.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar a frequencia: " + ex.getMessage());
            return -1;
        }
    }

    public static int getAulasMinistradasTurmaDisciplina(long idTurma, long idDisciplina) {
        try {
            return Frequencia.findWithQuery(Frequencia.class, SELECT_AULAS_MINISTRADAS_TURMA_DISCIPLINA, String.valueOf(idTurma), String.valueOf(idDisciplina)).size();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar as aulas ministradas: " + ex.getMessage());
            return 0;
        }
    }

    public static int getFrequenciaTurmaDisciplinaAluno(long idTurmaAluno, long idDisciplina) {
        try {
            return Frequencia.findWithQuery(Frequencia.class, SELECT_FREQUENCIA_TURMA_DISCIPLINA_ALUNO, String.valueOf(idTurmaAluno), String.valueOf(idDisciplina)).size();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a frequencia do aluno: " + ex.getMessage());
            return 0;
        }
    }

    public static Frequencia getFrequenciaExistente(long idTurmaAluno, long idDisciplina, int numeroAula) {
        try {
            return Frequencia.findWithQuery(Frequencia.class, SELECT_FREQUENCIA_EXISTENTE, String.valueOf(idTurmaAluno), String.valueOf(idDisciplina), String.valueOf(numeroAula)).get(0);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a frequencia existente: " + ex.getMessage());
            return null;
        }
    }
}
