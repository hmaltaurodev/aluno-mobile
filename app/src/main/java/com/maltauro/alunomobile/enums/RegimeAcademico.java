package com.maltauro.alunomobile.enums;

public enum RegimeAcademico {
    SEMESTRAL("Semestral"),
    ANUAL("Anual");

    private String descricao;

    RegimeAcademico(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
