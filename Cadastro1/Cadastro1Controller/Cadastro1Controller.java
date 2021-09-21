package com.example.varzeando.Cadastro1.Cadastro1Controller;

import android.app.Application;

import com.example.varzeando.Cadastro1.RepositoriesCadastro1.Cadastro1Repositories;

public class Cadastro1Controller {
    //Declaração das variáveis
    private static Cadastro1Controller instance = null;
    private final Application mApplication;

    //Construtor passando a application
    private Cadastro1Controller(Application application){
        mApplication = application;
    }

    //Criando um singletoon para garantir que sempre só tenha uma instância para a classe Model
    public static Cadastro1Controller getInstance(Application application){
        if(instance == null){
            instance = new Cadastro1Controller(application);
        }
        return instance;
    }

    //Criando método getmAplication para recuperar a aplication
    public Application getmApplication() {
        return mApplication;
    }

    //Declaração do método cadastro1 (primeira etapa)
    public void cadastro1(String nome, String dataNascimento, String email, String senha){
        Cadastro1Repositories cadastro1Repositories = new Cadastro1Repositories(mApplication);
        cadastro1Repositories.cadastro1(nome, dataNascimento, email, senha);
    }
}
