package com.maltauro.alunomobile.enums;

public enum GrauAcademico {
    BACHARELADO("Bacharelado"),
    TECNOLOGICO("Tecnólogico"),
    LICENCIATURA("Licenciatura"),
    ESPECIALIZACAO("Especialização"),
    MESTRADO("Mestrado"),
    DOUTORADO("Doutorado");

    private String descricao;

    GrauAcademico(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
