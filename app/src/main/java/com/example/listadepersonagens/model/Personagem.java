package com.example.listadepersonagens.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Personagem implements Serializable {
    //variaveis das informações que contem no personagem
    private String nome;
    private String altura;
    private String nascimento;
    private String telefone;
    private String endereco;
    private String CEP;
    private String RG;
    private String genero;
    private int id = 0;

    //classe de personagem utilizando as variaveis criadas como parametro
    public Personagem(String nome, String altura, String nascimento, String telefone, String endereco, String CEP, String RG, String genero) {
        //referenciando o conteudo das variaveis
        this.nome = nome;
        this.altura = altura;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.CEP = CEP;
        this.RG = RG;
        this.genero = genero;
    }
    // classe personagem vazia utilizada no formulario
    public Personagem() {
    }
    //gets e sets
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }


    @NonNull
    @Override
    public String toString() {
        return nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean IdValido() {
        return id > 0;
    }
}

