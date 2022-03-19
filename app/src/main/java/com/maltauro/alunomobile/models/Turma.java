package com.maltauro.alunomobile.models;

import androidx.annotation.NonNull;
import com.maltauro.alunomobile.enums.AnoAcademico;
import com.maltauro.alunomobile.enums.RegimeAcademico;
import com.orm.SugarRecord;

public class Turma extends SugarRecord {

    private Curso curso;
    private int anoAcademico;
    private int regimeAcademico;
    private int anoPeriodo;
    private int semestrePeriodo;

    public Turma() { }

    public Turma(Curso curso, int anoAcademico, int regimeAcademico, int anoPeriodo, int semestrePeriodo) {
        this.curso = curso;
        this.anoAcademico = anoAcademico;
        this.regimeAcademico = regimeAcademico;
        this.anoPeriodo = anoPeriodo;
        this.semestrePeriodo = semestrePeriodo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public AnoAcademico getAnoAcademico() {
        return AnoAcademico.getById(anoAcademico);
    }

    public void setAnoAcademico(AnoAcademico anoAcademico) {
        this.anoAcademico = anoAcademico.getId();
    }

    public RegimeAcademico getRegime() {
        return RegimeAcademico.getById(regimeAcademico);
    }

    public void setRegime(RegimeAcademico regimeAcademico) {
        this.regimeAcademico = regimeAcademico.getId();
    }

    public int getAnoPeriodo() {
        return anoPeriodo;
    }

    public void setAnoPeriodo(int anoPeriodo) {
        this.anoPeriodo = anoPeriodo;
    }

    public int getSemestrePeriodo() {
        return semestrePeriodo;
    }

    public void setSemestrePeriodo(int semestrePeriodo) {
        this.semestrePeriodo = semestrePeriodo;
    }

    @NonNull
    @Override
    public String toString() {
        if (this.semestrePeriodo != 0)
            return String.format("%o/%o - %s", this.anoPeriodo, this.semestrePeriodo, this.curso.getDescricao());

        return String.format("%o - %s", this.anoPeriodo, this.curso.getDescricao());
    }
}
