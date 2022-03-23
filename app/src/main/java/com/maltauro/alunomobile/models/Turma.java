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
        return String.format("%o - %s", anoPeriodo, this.gradeCurricular.toString());
    }
}
