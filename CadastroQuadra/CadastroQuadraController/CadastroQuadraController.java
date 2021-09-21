package com.example.varzeando.CadastroQuadra.CadastroQuadraController;

import android.app.Application;

import com.example.varzeando.CadastroQuadra.CadastroQuadraRepositories.CadastroQuadraRepositories;

public class CadastroQuadraController {
    //Declaração das variáveis
    private static CadastroQuadraController instance = null;
    private final Application mApplication;

    //Construtor passando a application
    private CadastroQuadraController(Application application){
        mApplication = application;
    }

    //Criando um singletoon para garantir que sempre só tenha uma instância para a classe Model
    public static CadastroQuadraController getInstance(Application application){
        if(instance == null){
            instance = new CadastroQuadraController(application);
        }
        return instance;
    }

    //Criando método getmAplication para recuperar a aplication
    public Application getmApplication() {
        return mApplication;
    }

    //Declaração do método adicionarQuadra
    public void adicionarQuadra(String nomeQuadra, String descricaoQuadra, Double latitude, Double longitude, String endereco, String url, Float avaliacao){
        CadastroQuadraRepositories cadastroQuadraRepositories = new CadastroQuadraRepositories(mApplication);
        cadastroQuadraRepositories.adicionarQuadra(nomeQuadra, descricaoQuadra, latitude, longitude, endereco, url, avaliacao);
    }
}
