package com.example.varzeando.Models;

import android.util.Log;

import org.json.JSONObject;

public class Partida {

    Integer id;
    String imagemPartida;
    String nomePartida;
    String enderecoPartida;
    String dataInicioPartida;
    String horarioPartida;
    String descricaoPartida;
    Integer numeroPessoasPartida;

    public Partida(Integer id, String imagemPartida, String nomePartida, String enderecoPartida, String dataInicioPartida, Integer numeroPessoasPartida, String descricaoPartida){
        this.id = id;
        this.imagemPartida = imagemPartida;
        this.nomePartida = nomePartida;
        this.enderecoPartida = enderecoPartida;
        this.dataInicioPartida = dataInicioPartida.substring(0, 5);
        this.horarioPartida = dataInicioPartida.substring(11, 16);
        this.numeroPessoasPartida = numeroPessoasPartida;
        this.descricaoPartida = descricaoPartida;
    }

    public String getImagemPartida(){
        return imagemPartida;
    }

    public void setImagemPartida(String imagemPartida){
        this.imagemPartida = imagemPartida;
    }

    public String getNomePartida(){
        return nomePartida;
    }

    public void setNomePartida(String nomePartida){
        this.nomePartida = nomePartida;
    }

    public String getEnderecoPartida(){
        return enderecoPartida;
    }

    public void setEnderecoPartida(String enderecoPartida){
        this.enderecoPartida = enderecoPartida;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataInicioPartida() {
        return dataInicioPartida;
    }

    public void setDataInicioPartida(String dataInicioPartida) {
        this.dataInicioPartida = dataInicioPartida;
    }

    public String getHorarioPartida() {
        return horarioPartida;
    }

    public void setHorarioPartida(String horarioPartida) {
        this.horarioPartida = horarioPartida;
    }

    public String getDescricaoPartida() {
        return descricaoPartida;
    }

    public void setDescricaoPartida(String descricaoPartida) {
        this.descricaoPartida = descricaoPartida;
    }

    public Integer getNumeroPessoas() {
        return numeroPessoasPartida;
    }

    public void setNumeroPessoas(Integer numeroPessoas) {
        this.numeroPessoasPartida = numeroPessoas;
    }

    public String getDataPartida(){
        return this.dataInicioPartida;
    }

    public void setDataPartida(String dataPartida){
        this.dataInicioPartida = dataPartida;
    }

    public String getHoraPartida(){
        return this.horarioPartida;
    }

    public void setHoraPartida(String horaPartida){
        this.horarioPartida = horaPartida;
    }

}
