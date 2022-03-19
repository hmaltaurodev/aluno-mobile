package com.maltauro.alunomobile.enums;

public enum AnoAcademico {
    PRIMEIRO("1º Ano"),
    SEGUNDO("2º Ano"),
    TERCEIRO("3º Ano"),
    QUARTO("4º Ano"),
    QUINTO("5º Ano");

    private String descricao;

    AnoAcademico(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
