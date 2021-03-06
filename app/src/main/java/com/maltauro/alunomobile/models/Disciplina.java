package com.maltauro.alunomobile.models;

import androidx.annotation.NonNull;
import com.orm.SugarRecord;

public class Disciplina extends SugarRecord {

    private String descricao;
    private int horasAulas;
    private int quantidadeAulas;
    private Professor professor;

    public Disciplina() { }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getHorasAulas() {
        return horasAulas;
    }

    public void setHorasAulas(int horasAulas) {
        this.horasAulas = horasAulas;
    }

    public int getQuantidadeAulas() {
        return quantidadeAulas;
    }

    public void setQuantidadeAulas(int quantidadeAulas) {
        this.quantidadeAulas = quantidadeAulas;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @NonNull
    @Override
    public String toString() {
        return descricao;
    }
}
