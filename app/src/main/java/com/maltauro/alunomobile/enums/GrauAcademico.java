package com.maltauro.alunomobile.enums;

import androidx.annotation.NonNull;

public enum GrauAcademico {
    BACHARELADO("Bacharelado", 1),
    TECNOLOGICO("Tecnólogico", 2),
    LICENCIATURA("Licenciatura", 3),
    ESPECIALIZACAO("Especialização", 4),
    MESTRADO("Mestrado", 5),
    DOUTORADO("Doutorado", 6);

    private final String descricao;
    private final int id;

    GrauAcademico(String descricao, int id){
        this.descricao = descricao;
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return this.descricao;
    }

    public int getId() {
        return id;
    }

    public static GrauAcademico getById(int id) {
        for (GrauAcademico grauAcademico : values())
            if (grauAcademico.id == id)
                return grauAcademico;

        return null;
    }
}
