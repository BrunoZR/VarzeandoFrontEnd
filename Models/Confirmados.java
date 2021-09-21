package com.example.varzeando.Models;

public class Confirmados {

    int imagemPessoa;
    String nomePessoa;
    String pocicaoPessoa;
    int imagemEstrela;

    public Confirmados(int imagemPessoa, String nomePessoa, String pocicaoPessoa, int imagemEstrela){
        this.imagemPessoa = imagemPessoa;
        this.nomePessoa = nomePessoa;
        this.pocicaoPessoa = pocicaoPessoa;
        this.imagemEstrela = imagemEstrela;
    }

    public int getImagemPessoa(){
        return imagemPessoa;
    }

    public void setImagemPessoa(int imagemPessoa){
        this.imagemPessoa = imagemPessoa;
    }

    public String getNomePessoa(){
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa){
        this.nomePessoa = nomePessoa;
    }

    public String getPocicaoPessoa(){
        return pocicaoPessoa;
    }

    public void setPocicaoPessoa(String pocicaoPessoa){
        this.pocicaoPessoa = pocicaoPessoa;
    }

    public int getImagemEstrela(){
        return imagemEstrela;
    }

    public void setImagemEstrela(int imagemEstrela){
        this.imagemEstrela = imagemEstrela;
    }
}
