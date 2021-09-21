package com.example.varzeando.Models;

import android.net.Uri;

public class ImagensQuadra {
    String nomeImagem;
    Uri uri;

    public ImagensQuadra(){

    }

    public ImagensQuadra(String nomeImagem){
        this.nomeImagem = nomeImagem;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem){
        this.nomeImagem = nomeImagem;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri){
        this.uri = uri;
    }

}
