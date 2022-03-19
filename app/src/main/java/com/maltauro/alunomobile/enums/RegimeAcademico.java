package com.maltauro.alunomobile.enums;

import androidx.annotation.NonNull;

public enum RegimeAcademico {
    SEMESTRAL("Semestral", 1),
    ANUAL("Anual", 2);

    private final String descricao;
    private final int id;

    RegimeAcademico(String descricao, int id) {
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

    public static RegimeAcademico getById(int id) {
        for (RegimeAcademico regimeAcademico : values())
            if (regimeAcademico.id == id)
                return regimeAcademico;

        return null;
    }
}
