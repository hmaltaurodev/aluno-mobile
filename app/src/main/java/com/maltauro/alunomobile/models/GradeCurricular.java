package com.maltauro.alunomobile.models;

import androidx.annotation.NonNull;
import com.maltauro.alunomobile.enums.AnoAcademico;
import com.maltauro.alunomobile.enums.RegimeAcademico;
import com.maltauro.alunomobile.enums.SemestrePeriodo;
import com.orm.SugarRecord;
import java.util.Objects;

public class GradeCurricular extends SugarRecord {

    private Curso curso;
    private int anoAcademico;
    private int regimeAcademico;
    private int semestrePeriodo;

    public GradeCurricular() { }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public AnoAcademico getAnoAcademico() {
        return AnoAcademico.getById(anoAcademico);
    }

    public void setAnoAcademico(int anoAcademico) {
        this.anoAcademico = anoAcademico;
    }

    public RegimeAcademico getRegimeAcademico() {
        return RegimeAcademico.getById(regimeAcademico);
    }

    public void setRegimeAcademico(int regimeAcademico) {
        this.regimeAcademico = regimeAcademico;
    }

    public SemestrePeriodo getSemestrePeriodo() {
        return SemestrePeriodo.getById(semestrePeriodo);
    }

    public void setSemestrePeriodo(int semestrePeriodo) {
        this.semestrePeriodo = semestrePeriodo;
    }

    @NonNull
    @Override
    public String toString() {
        String anoAcademico = this.getAnoAcademico().toString();

        if (this.semestrePeriodo != 0)
            return String.format("%s/%s - %s", anoAcademico, this.getRegimeAcademico().toString(), this.curso.getDescricao());
        return String.format("%s - %s", anoAcademico, this.curso.getDescricao());
    }
}
