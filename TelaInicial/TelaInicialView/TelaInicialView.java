package com.example.varzeando.TelaInicial.TelaInicialView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.varzeando.Cadastro1.ViewCadastro1.TelaCadastro1;
import com.example.varzeando.ClassesSuporte.DbHelper;
import com.example.varzeando.Login.LoginView.TelaLogin;
import com.example.varzeando.MenuInferior.MenuInferiorView.TelaMenuInferior;
import com.example.varzeando.R;
import com.example.varzeando.ClassesSuporte.SessionManagement;

public class TelaInicialView extends AppCompatActivity {

    //Declaração das variáveis do programa
    private Button botaoEscolhaEntrar;
    private Button botaoEscolhaCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Localização das widgets do xml (botões)
        botaoEscolhaCadastro = findViewById(R.id.btn_escolhaCadastrar);
        botaoEscolhaEntrar = findViewById(R.id.btn_escolhaEntrar);

        //Definição de um clickListener para o botão de cadastro na tela inicial (encaminha para a primeira etapa da tela de cadastro)
        botaoEscolhaCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaInicialView.this, TelaCadastro1.class);
                startActivity(intent);
            }
        });

        //Definição de um clickListener para o botão de entrar na tela inicial (encaminha para a tela de login)
        botaoEscolhaEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaInicialView.this, TelaLogin.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkSession();
    }

    private void checkSession() {
        SessionManagement sessionManagement = new SessionManagement(TelaInicialView.this);
        Integer userId = sessionManagement.getIdSession();

        if(userId != -1){
            Intent intent = new Intent(TelaInicialView.this, TelaMenuInferior.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}