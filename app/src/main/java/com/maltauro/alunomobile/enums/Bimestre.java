package com.maltauro.alunomobile.enums;

import androidx.annotation.NonNull;

public enum Bimestre {
    PRIMEIRO("1º Bimestre", 1),
    SEGUNDO("2º Bimestre", 2),
    TERCEIRO("3º Bimestre", 3),
    QUARTO("4º Bimestre", 4);

    private final String descricao;
    private final int id;

    Bimestre(String descricao, int id) {
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

    public static Bimestre getById(int id) {
        for (Bimestre bimestre : values())
            if (bimestre.id == id)
                return bimestre;

        return null;
    }
}
