package com.example.varzeando.Login.LoginView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.varzeando.Login.LoginController.LoginController;
import com.example.varzeando.MenuInferior.MenuInferiorView.TelaMenuInferior;
import com.example.varzeando.R;
import com.example.varzeando.Cadastro1.ViewCadastro1.TelaCadastro1;

public class TelaLogin extends AppCompatActivity {
    //Declaração das variáveis do programa
    private TextView tv_cadastreseLogin;
    private EditText campoEmail;
    private EditText campoSenha;
    private Button botaoEntrar;
    private Boolean sucessoAoLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        //Localização das widgets do xml
        tv_cadastreseLogin = findViewById(R.id.tv_cadastreseLogin);
        campoEmail = findViewById(R.id.et_emailLogin);
        campoSenha = findViewById(R.id.et_senhaLogin);
        botaoEntrar = findViewById(R.id.btn_entrar);

        //Definir um clickListener para o botão de login para validar os dados do usuário e reencaminhar ele caso estejam corretos
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                LoginController loginController = LoginController.getInstance(TelaLogin.this.getApplication());
                loginController.login(email,senha);
                Intent intent = new Intent(getApplicationContext(), TelaMenuInferior.class);
                startActivity(intent);
            }
        });

        //Definição de um clickListener para o texto "Cadastre-se" destacado em verde na tela de login (encaminha para a primeira etapa do cadastro)
        tv_cadastreseLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLogin.this, TelaCadastro1.class);
                startActivity(intent);
            }
        });
    }
}