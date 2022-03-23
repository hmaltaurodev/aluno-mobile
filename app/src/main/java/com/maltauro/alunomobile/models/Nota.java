package com.maltauro.alunomobile.models;

import com.maltauro.alunomobile.enums.Bimestre;
import com.orm.SugarRecord;

public class Nota extends SugarRecord {

    private TurmaAluno turmaAluno;
    private int bimestre;
    private double nota;

    public Nota() { }

    public Nota(TurmaAluno turmaAluno, int bimestre, double nota) {
        this.turmaAluno = turmaAluno;
        this.bimestre = bimestre;
        this.nota = nota;
    }

    public TurmaAluno getTurmaAluno() {
        return turmaAluno;
    }

    public void setTurmaAluno(TurmaAluno turmaAluno) {
        this.turmaAluno = turmaAluno;
    }

    public Bimestre getBimestre() {
        return Bimestre.getById(bimestre);
    }

    public void setBimestre(Bimestre bimestre) {
        this.bimestre = bimestre.getId();
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
