package com.example.varzeando.Models;

import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

public class Usuario {
    Integer id;
    String nome;
    String email;
    double latitude;
    double longitude;
    String posicao;
    String token;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(Integer id, String nome, String token) {
        this.id = id;
        this.nome = nome;
        this.token = token;
    }

    public Usuario(int id, String nome, String email, double latitude, double longitude, String posicao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.posicao = posicao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
