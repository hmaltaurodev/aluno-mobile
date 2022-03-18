package com.maltauro.alunomobile.models;

import com.orm.SugarRecord;

public class Aluno extends SugarRecord {

    private int ra;
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String dataMatricula;

    public Aluno() { }

    public Aluno(int ra, String nome, String cpf, String dataNascimento, String dataMatricula) {
        this.ra = ra;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.dataMatricula = dataMatricula;
    }

    public int getRa() {
        return ra;
    }

    public void setRa(int ra) {
        this.ra = ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(String dataMatricula) {
        this.dataMatricula = dataMatricula;
    }
}
