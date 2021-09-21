package com.example.varzeando.DetalhesQuadra.DetalhesQuadraController;

import android.app.Application;

import com.example.varzeando.DetalhesQuadra.DetalhesQuadraRepositories.DetalhesQuadraRepositories;

public class DetalhesQuadraController {
    //Declaração das variáveis
    private static DetalhesQuadraController instance = null;
    private final Application mApplication;

    //Construtor passando a application
    public DetalhesQuadraController(Application application){
        mApplication = application;
    }

    //Criando um singletoon para garantir que sempre só tenha uma instância para a classe Model
    public static DetalhesQuadraController getInstance(Application application){
        if(instance == null){
            instance = new DetalhesQuadraController(application);
        }
        return instance;
    }

    //Criando método getmAplication para recuperar a aplication
    public Application getmApplication() {
        return mApplication;
    }

    //Declaração do método cadastro1 (primeira etapa)
    public void avaliarQuadra(Integer idQuadra, Float avaliacao){
        DetalhesQuadraRepositories detalhesQuadraRepositories = new DetalhesQuadraRepositories(mApplication);
        detalhesQuadraRepositories.avaliarQuadra(idQuadra, avaliacao);
    }
}
