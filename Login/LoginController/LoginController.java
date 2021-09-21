package com.example.varzeando.Login.LoginController;

import android.app.Application;

import com.example.varzeando.Login.LoginRepositories.LoginRepositories;

public class LoginController {
    //Declaração das variáveis
    private static LoginController instance = null;
    private final Application mApplication;

    //Construtor passando a application
    private LoginController(Application application){
        mApplication = application;
    }

    //Criando um singletoon para garantir que sempre só tenha uma instância para a classe Model
    public static LoginController getInstance(Application application){
        if(instance == null){
            instance = new LoginController(application);
        }
        return instance;
    }

    //Criando método getmAplication para recuperar a aplication
    public Application getmApplication() {
        return mApplication;
    }

    //Declaração do método cadastro1 (primeira etapa)
    public void login(String email, String senha){
        LoginRepositories loginRepositories = new LoginRepositories(mApplication);
        loginRepositories.login(email, senha);
    }
}
