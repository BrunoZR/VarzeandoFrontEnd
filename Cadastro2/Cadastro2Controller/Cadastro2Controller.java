package com.example.varzeando.Cadastro2.Cadastro2Controller;

import android.app.Application;
import android.util.Log;

import com.example.varzeando.Cadastro1.RepositoriesCadastro1.Cadastro1Repositories;
import com.example.varzeando.Cadastro2.Cadastro2Repositories.Cadastro2Repositories;

public class Cadastro2Controller {
    //Declaração das variáveis
    private static Cadastro2Controller instance = null;
    private final Application mApplication;

    //Construtor passando a application
    private Cadastro2Controller(Application application){
        mApplication = application;
    }

    //Criando um singletoon para garantir que sempre só tenha uma instância para a classe Model
    public static Cadastro2Controller getInstance(Application application){
        if(instance == null){
            instance = new Cadastro2Controller(application);
        }
        return instance;
    }

    //Criando método getmAplication para recuperar a aplication
    public Application getmApplication() {
        return mApplication;
    }

    //Declaração do método cadastro2 (segunda etapa)
    public void cadastro2(String email, String posicao, Double latitude, Double longitude){
        Cadastro2Repositories cadastro2Repositories = new Cadastro2Repositories(mApplication);
        cadastro2Repositories.cadastro2(email, posicao, latitude, longitude);
    }
}
