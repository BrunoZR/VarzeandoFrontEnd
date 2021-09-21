package com.example.varzeando.Models;

import java.util.ArrayList;

public class Quadra {

    private Integer id;
    private String imagemQuadra;
    private String nomeQuadra;
    private String enderecoQuadra;
    private String descricaoQuadra;
    private Double avaliacao;
    private Integer quantidade;
    private Double latitude;
    private Double longitude;

    public Quadra(Integer id, String nomeQuadra, String descricaoQuadra, String enderecoQuadra, String imagemQuadra) {
        this.id = id;
        this.imagemQuadra = imagemQuadra;
        this.nomeQuadra = nomeQuadra;
        this.enderecoQuadra = enderecoQuadra;
        this.descricaoQuadra = descricaoQuadra;
    }

    public Quadra(Integer id, String nomeQuadra, String descricaoQuadra, String enderecoQuadra, String imagemQuadra, Double avaliacao, Integer quantidade, Double latitude, Double longitude) {
        this.id = id;
        this.imagemQuadra = imagemQuadra;
        this.nomeQuadra = nomeQuadra;
        this.enderecoQuadra = enderecoQuadra;
        this.descricaoQuadra = descricaoQuadra;
        this.avaliacao = avaliacao;
        this.quantidade = quantidade;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Quadra(String imagemQuadra, String nomeQuadra, String enderecoQuadra, Integer id) {
        this.id = id;
        this.imagemQuadra = imagemQuadra;
        this.nomeQuadra = nomeQuadra;
        this.enderecoQuadra = enderecoQuadra;
    }

    public String getImagemQuadra() {
        return imagemQuadra;
    }

    public void setImagemQuadra(String imagemQuadra) {
        this.imagemQuadra = imagemQuadra;
    }

    public String getNomeQuadra() {
        return nomeQuadra;
    }

    public void setNomeQuadra(String nomeQuadra) {
        this.nomeQuadra = nomeQuadra;
    }

    public String getEnderecoQuadra() {
        return enderecoQuadra;
    }

    public void setEnderecoQuadra(String enderecoQuadra) {
        this.enderecoQuadra = enderecoQuadra;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricaoQuadra() {
        return descricaoQuadra;
    }

    public void setDescricaoQuadra(String descricaoQuadra) {
        this.descricaoQuadra = descricaoQuadra;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
