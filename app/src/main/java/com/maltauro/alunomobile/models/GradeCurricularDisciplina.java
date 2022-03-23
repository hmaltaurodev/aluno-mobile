package com.maltauro.alunomobile.models;

import com.orm.SugarRecord;

public class GradeCurricularDisciplina extends SugarRecord {

    private GradeCurricular gradeCurricular;
    private Disciplina disciplina;

    public GradeCurricularDisciplina() { }

    public GradeCurricular getGradeCurricular() {
        return gradeCurricular;
    }

    public void setGradeCurricular(GradeCurricular gradeCurricular) {
        this.gradeCurricular = gradeCurricular;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
}
