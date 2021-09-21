package com.example.varzeando.Home.HomeController;

import android.app.Application;

import com.example.varzeando.Adapters.RecyclerViewAdapterPartida;
import com.example.varzeando.Adapters.RecyclerViewAdapterPartidasNessaSemana;
import com.example.varzeando.Adapters.RecyclerViewAdapterQuadras;
import com.example.varzeando.Adapters.RecyclerViewAdapterQuadrasProximas;
import com.example.varzeando.Home.HomeRepositories.HomeRepositories;
import com.example.varzeando.Models.Quadra;

import java.util.ArrayList;

public class HomeController {
    //Declaração das variáveis
    private static HomeController instance = null;
    private final Application mApplication;
    private HomeRepositories homeRepositories;

    //Construtor passando a application
    public HomeController(Application application){
        mApplication = application;
        homeRepositories = new HomeRepositories(mApplication);
    }

    //Criando um singletoon para garantir que sempre só tenha uma instância para a classe Model
    public static HomeController getInstance(Application application){
        if(instance == null){
            instance = new HomeController(application);
        }
        return instance;
    }

    //Criando método getmAplication para recuperar a aplication
    public Application getmApplication() {
        return mApplication;
    }



    public void loadQuadrasProximas(RecyclerViewAdapterQuadrasProximas rv, Double latitude, Double longitude){
        homeRepositories.loadAtributosQuadrasProximas(rv, latitude, longitude);
    }

    public void loadTodasPartidas(RecyclerViewAdapterPartida rv){
        homeRepositories.loadAtributosTodasPartidas(rv);
    }

    public void loadPartidasNessaSemana(RecyclerViewAdapterPartidasNessaSemana rv){
        homeRepositories.loadAtributosPartidasNessaSemana(rv);
    }
}
