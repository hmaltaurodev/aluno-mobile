package com.maltauro.alunomobile.models;

import androidx.annotation.NonNull;
import com.orm.SugarRecord;

public class Turma extends SugarRecord {

    private GradeCurricular gradeCurricular;
    private int anoPeriodo;

    public Turma() { }

    public GradeCurricular getGradeCurricular() {
        return gradeCurricular;
    }

    public void setGradeCurricular(GradeCurricular gradeCurricular) {
        this.gradeCurricular = gradeCurricular;
    }

    public int getAnoPeriodo() {
        return anoPeriodo;
    }

    public void setAnoPeriodo(int anoPeriodo) {
        this.anoPeriodo = anoPeriodo;
    }

    @NonNull
    @Override
    public String toString() {
        String anoAcademico = this.gradeCurricular.getAnoAcademico().toString();

        if (this.gradeCurricular.getSemestrePeriodo() != null)
            return String.format("%s - %s/%s", this.anoPeriodo, anoAcademico, this.gradeCurricular.getSemestrePeriodo().toString());
        return String.format("%s - %s", this.anoPeriodo, anoAcademico);
    }
}
