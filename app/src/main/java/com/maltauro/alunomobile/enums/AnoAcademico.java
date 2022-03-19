package com.maltauro.alunomobile.enums;

import androidx.annotation.NonNull;

public enum AnoAcademico {
    PRIMEIRO("1º Ano", 1),
    SEGUNDO("2º Ano", 2),
    TERCEIRO("3º Ano", 3),
    QUARTO("4º Ano", 4),
    QUINTO("5º Ano", 5);

    private final String descricao;
    private final int id;

    AnoAcademico(String descricao, int id) {
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

    public static AnoAcademico getById(int id) {
        for (AnoAcademico anoAcademico : values())
            if (anoAcademico.id == id)
                return anoAcademico;

        return null;
    }
}
