package com.maltauro.alunomobile.dao;

import android.util.Log;
import com.maltauro.alunomobile.models.Curso;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public static long salvar(Curso curso) {
        try {
            return curso.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar o curso: " + ex.getMessage());
            return -1;
        }
    }

    public static List<Curso> getListCursos(String where, String[] whererArgs, String orderBy) {
        List<Curso> cursos = new ArrayList<>();

        try {
            cursos = Curso.find(Curso.class, where, whererArgs, null, orderBy, null);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de cursos: " + ex.getMessage());
        }

        return cursos;
    }
}
