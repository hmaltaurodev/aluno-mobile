package com.maltauro.alunomobile.dao;

import android.util.Log;
import com.maltauro.alunomobile.models.Disciplina;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    public static long salvar(Disciplina disciplina) {
        try {
            return disciplina.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar o disciplina: " + ex.getMessage());
            return -1;
        }
    }

    public static Disciplina getDisciplina(int id) {
        try {
            return Disciplina.findById(Disciplina.class, id);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar o disciplina: " + ex.getMessage());
            return null;
        }
    }

    public static List<Disciplina> getListDisciplinas(String where, String[] whererArgs, String orderBy) {
        List<Disciplina> disciplinas = new ArrayList<>();

        try {
            disciplinas = Disciplina.find(Disciplina.class, where, whererArgs, null, orderBy, null);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de disciplinas: " + ex.getMessage());
        }

        return disciplinas;
    }

    public static boolean delete(Disciplina disciplina) {
        try {
            return Disciplina.delete(disciplina);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao deletar o disciplina: " + ex.getMessage());
            return false;
        }
    }
}
