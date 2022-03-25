package com.maltauro.alunomobile.models;

import androidx.annotation.NonNull;
import com.maltauro.alunomobile.enums.GrauAcademico;
import com.orm.SugarRecord;

public class Curso extends SugarRecord {

    private int codigoMEC;
    private String descricao;
    private int grauAcademico;

    public Curso() { }

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

    public GrauAcademico getGrauAcademico() {
        return GrauAcademico.getById(grauAcademico);
    }

    public void setGrauAcademico(GrauAcademico grauAcademico) {
        this.grauAcademico = grauAcademico.getId();
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s - %s", this.codigoMEC, this.descricao);
    }
}
