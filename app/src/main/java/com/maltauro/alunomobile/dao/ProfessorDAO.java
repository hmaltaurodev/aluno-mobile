package com.maltauro.alunomobile.dao;

import android.util.Log;
import com.maltauro.alunomobile.models.Professor;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    public static long salvar(Professor professor) {
        try {
            return professor.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar o professor: " + ex.getMessage());
            return -1;
        }
    }

    public static Professor getProfessor(int id) {
        try {
            return Professor.findById(Professor.class, id);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar o professor: " + ex.getMessage());
            return null;
        }
    }

    public static List<Professor> getListAlunos(String where, String[] whererArgs, String orderBy) {
        List<Professor> professores = new ArrayList<>();

        try {
            professores = Professor.find(Professor.class, where, whererArgs, null, orderBy, null);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de professores: " + ex.getMessage());
        }

        return professores;
    }

    public static boolean delete(Professor professor) {
        try {
            return Professor.delete(professor);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao deletar o professor: " + ex.getMessage());
            return false;
        }
    }
}
