package com.maltauro.alunomobile.dao;

import android.util.Log;
import com.maltauro.alunomobile.models.Nota;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    private static final String SELECT_NOTA_EXISTENTE =
            "SELECT * " +
            "FROM NOTA " +
            "WHERE TURMA_ALUNO = ? " +
            "  AND DISCIPLINA = ? " +
            "  AND BIMESTRE = ? ";

    public static long salvar(Nota nota) {
        try {
            return nota.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar a nota: " + ex.getMessage());
            return -1;
        }
    }

    public static Nota getNota(int id) {
        try {
            return Nota.findById(Nota.class, id);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a nota: " + ex.getMessage());
            return null;
        }
    }

    public static Nota getNotaExistente(String... arguments) {
        try {
            return Nota.findWithQuery(Nota.class, SELECT_NOTA_EXISTENTE, arguments).get(0);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de notas: " + ex.getMessage());
            return null;
        }
    }

    public static List<Nota> getListNotas(String where, String[] whererArgs, String orderBy) {
        List<Nota> notas = new ArrayList<>();

        try {
            notas = Nota.find(Nota.class, where, whererArgs, null, orderBy, null);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de notas: " + ex.getMessage());
        }

        return notas;
    }
}
