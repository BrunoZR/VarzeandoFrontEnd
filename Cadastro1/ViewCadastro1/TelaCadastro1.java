package com.example.varzeando.Cadastro1.ViewCadastro1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.varzeando.Cadastro1.Cadastro1Controller.Cadastro1Controller;
import com.example.varzeando.TelaInicial.TelaInicialView.TelaInicialView;
import com.example.varzeando.R;
import com.example.varzeando.Cadastro2.ViewCadastro2.TelaCadastro2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TelaCadastro1 extends AppCompatActivity {

    //Declaração das variáveis do programa
    private ConstraintLayout constraintPopUp;
    private ConstraintLayout constraintCadastro;
    private EditText campoNome;
    private EditText campoDataNascimento;
    private EditText campoEmail;
    private EditText campoSenha;
    private RadioButton termosDeUso;
    private Button botaoCadastrar;
    private DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro1);

        //Localização das widgets do xml (constraints) e definir a visibilidade deles -> PopUp fica invisível e a principal fica visível
        constraintPopUp = findViewById(R.id.constraintPopUp);
        constraintPopUp.setVisibility(View.INVISIBLE);

        constraintCadastro = findViewById(R.id.constraintCadastro);
        constraintCadastro.setVisibility(View.VISIBLE);

        //Localização das demais widgets do xml
        campoNome = findViewById(R.id.et_nomeCadastro);
        campoDataNascimento = findViewById(R.id.et_dataNascimentoCadastro);
        campoEmail = findViewById(R.id.et_emailCadastro);
        campoSenha = findViewById(R.id.et_senhaCadastro);
        termosDeUso = findViewById(R.id.rb_termosCadastro);
        botaoCadastrar = findViewById(R.id.btn_cadastrarCadastro);

        //Definir um clickListener para o botão de cadastro para validar enviar os dados do usuário para a API
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //Recuperar o radioButton
                Boolean aceitouTermosDeUso = termosDeUso.isChecked();

                //If pra validar se os campos são inválidos/nulos
                if(campoNome.getText().toString().trim().equals("") ){
                    Toast.makeText(TelaCadastro1.this, R.string.colocar_nome_completo, Toast.LENGTH_SHORT).show();
                }else if(campoEmail.getText().toString().trim().equals("") ){
                    Toast.makeText(TelaCadastro1.this, R.string.colocar_email ,Toast.LENGTH_SHORT).show();
                }else if(campoSenha.getText().toString().trim().equals("") || campoSenha.getText().toString().length() < 6){
                    Toast.makeText(TelaCadastro1.this, R.string.colocar_senha, Toast.LENGTH_SHORT).show();
                }

                //If e Else para checar se o botão está preenchido ou não e se a data é valida ou não
                else if(isDateValid(campoDataNascimento.getText().toString())){
                    if(aceitouTermosDeUso){
                        //Recuperando os dados que foram inseridos nos espaço de texto do cadastro
                        String nome = campoNome.getText().toString();

                        //Só tem como passar como string, mas já que estamos usando o método isDateValid, garantimos que é uma data válida
                        String dataNascimento = campoDataNascimento.getText().toString();
                        String email = campoEmail.getText().toString();
                        String senha = campoSenha.getText().toString();

                        Cadastro1Controller cadastro1Controller = Cadastro1Controller.getInstance(TelaCadastro1.this.getApplication());
                        cadastro1Controller.cadastro1(nome,dataNascimento, email, senha);
                        mostrarPopUp(v);
                    } else {
                        Toast.makeText(TelaCadastro1.this, R.string.aceitar_termos_de_uso, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(TelaCadastro1.this, R.string.data_invalida, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Criação de método para mostrar o popUp que será exibido ao clicar no botão de continuar (método onClick)
    public void mostrarPopUp(View v){
        //Declaração das variáveis do popUp (os dois botões que o usuário pode escolher)
        Button botaoContinuar;
        Button botaoAgoraNao;

        //Localização das widgets do popUp
        botaoContinuar = findViewById(R.id.btn_continuarCadastro);
        botaoAgoraNao = findViewById(R.id.btn_agoraNaoCadastro);

        //Deixar o PopUp visível e definir o alpha da constraint principal como 0.5 (deixa com o efeito de desfocado no fundo da PopUp)
        constraintPopUp.setVisibility(View.VISIBLE);
        constraintCadastro.setAlpha((float)0.5);

        //Definição de um clickListener para o botão de continuar do PopUp (encaminha para a segunda etapa da tela de cadastro)
        botaoContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TelaCadastro2.class);
                intent.putExtra("email", campoEmail.getText().toString());
                Log.i("CADASTRO", campoEmail.getText().toString());
                startActivity(intent);
            }
        });

        //Definição de um clickListener para o botão de agora não do PopUp (encaminha para a tela inicial do aplicativo (provisoriamente enquanto não temos uma tela principal))
        botaoAgoraNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TelaInicialView.class);
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean isDateValid(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate d = LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}