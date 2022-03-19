package com.maltauro.alunomobile.models;

import androidx.annotation.NonNull;

import com.orm.SugarRecord;
import java.util.Objects;

public class Professor extends SugarRecord {

    private int ra;
    private String nome;
    private String cpf;
    private String dataNascimento;

    public Professor() { }

    public Professor(int ra, String nome, String cpf, String dataNascimento) {
        this.ra = ra;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
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

    @NonNull
    @Override
    public String toString() {
        return this.nome;
    }
}
