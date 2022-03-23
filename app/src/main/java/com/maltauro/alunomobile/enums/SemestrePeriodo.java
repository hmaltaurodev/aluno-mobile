package com.maltauro.alunomobile.enums;

import androidx.annotation.NonNull;

public enum SemestrePeriodo {
    PRIMEIRO("1º Semestre", 1),
    SEGUNDO("2º Semestre", 2);

    private final String descricao;
    private final int id;

    SemestrePeriodo(String descricao, int id) {
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

    public static SemestrePeriodo getById(int id) {
        for (SemestrePeriodo semestrePeriodo : values())
            if (semestrePeriodo.id == id)
                return semestrePeriodo;

        return null;
    }
}
