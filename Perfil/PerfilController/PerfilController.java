package com.example.varzeando.Perfil.PerfilController;

import android.app.Application;

import com.example.varzeando.Adapters.RecyclerViewAdapterPartida;
import com.example.varzeando.Adapters.RecyclerViewAdapterQuadras;
import com.example.varzeando.Home.HomeController.HomeController;
import com.example.varzeando.Home.HomeRepositories.HomeRepositories;
import com.example.varzeando.Models.Quadra;
import com.example.varzeando.Perfil.PerfilRepositories.PerfilRepositories;

import java.util.ArrayList;

public class PerfilController {
    //Declaração das variáveis
    private static PerfilController instance = null;
    private final Application mApplication;
    private PerfilRepositories perfilRepositories;

    //Construtor passando a application
    public PerfilController(Application application){
        mApplication = application;
        perfilRepositories = new PerfilRepositories(mApplication);
    }

    //Criando um singletoon para garantir que sempre só tenha uma instância para a classe Model
    public static PerfilController getInstance(Application application){
        if(instance == null){
            instance = new PerfilController(application);
        }
        return instance;
    }

    //Criando método getmAplication para recuperar a aplication
    public Application getmApplication() {
        return mApplication;
    }

    public void loadTodasPartidas(RecyclerViewAdapterPartida rv){
        perfilRepositories.loadAtributosTodasPartidas(rv);
    }
}
