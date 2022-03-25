package com.maltauro.alunomobile.dao;

import android.util.Log;
import com.maltauro.alunomobile.models.Frequencia;
import java.util.ArrayList;
import java.util.List;

public class FrequenciaDAO {

    public static long salvar(Frequencia frequencia) {
        try {
            return frequencia.save();
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao salvar a frequencia: " + ex.getMessage());
            return -1;
        }
    }

    public static Frequencia getFrequencia(int id) {
        try {
            return Frequencia.findById(Frequencia.class, id);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a frequencia: " + ex.getMessage());
            return null;
        }
    }

    public static List<Frequencia> getListFrequencias(String where, String[] whererArgs, String orderBy) {
        List<Frequencia> frequencias = new ArrayList<>();

        try {
            frequencias = Frequencia.find(Frequencia.class, where, whererArgs, null, orderBy, null);
        }
        catch (Exception ex) {
            Log.e("Erro", "Erro ao buscar a lista de frequencias: " + ex.getMessage());
        }

        return frequencias;
    }
}
