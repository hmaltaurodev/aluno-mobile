package com.maltauro.alunomobile.models;

import androidx.annotation.NonNull;
import com.maltauro.alunomobile.enums.GrauAcademico;
import com.orm.SugarRecord;

public class Curso extends SugarRecord {

    private int codigoMEC;
    private String descricao;
    private GrauAcademico grauAcademico;

    public Curso() { }

    public Curso(int codigoMEC, String descricao, GrauAcademico grauAcademico) {
        this.codigoMEC = codigoMEC;
        this.descricao = descricao;
        this.grauAcademico = grauAcademico;
    }

    public int getCodigoMEC() {
        return codigoMEC;
    }

    public void setCodigoMEC(int codigoMEC) {
        this.codigoMEC = codigoMEC;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public GrauAcademico getGrau() {
        return grauAcademico;
    }

    public void setGrau(GrauAcademico grauAcademico) {
        this.grauAcademico = grauAcademico;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%o - %s", this.codigoMEC, this.descricao);
    }
}
