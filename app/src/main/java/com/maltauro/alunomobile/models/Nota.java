package com.maltauro.alunomobile.models;

import com.maltauro.alunomobile.enums.Bimestre;
import com.orm.SugarRecord;

public class Nota extends SugarRecord {

    private TurmaAluno turmaAluno;
    private Disciplina disciplina;
    private int bimestre;
    private double nota;

    public Nota() { }

    public TurmaAluno getTurmaAluno() {
        return turmaAluno;
    }

    public void setTurmaAluno(TurmaAluno turmaAluno) {
        this.turmaAluno = turmaAluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Bimestre getBimestre() {
        return Bimestre.getById(bimestre);
    }

    public void setBimestre(int bimestre) {
        this.bimestre = bimestre;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
