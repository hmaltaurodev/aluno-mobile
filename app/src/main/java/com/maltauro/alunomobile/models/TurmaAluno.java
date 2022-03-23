package com.maltauro.alunomobile.models;

import com.orm.SugarRecord;

public class TurmaAluno extends SugarRecord {

    private Turma turma;
    private Aluno aluno;

    public TurmaAluno() { }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}
