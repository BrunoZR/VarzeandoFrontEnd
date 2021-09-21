package com.example.varzeando.DetalhesQuadra.DetalhesQuadraView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.varzeando.Cadastro2.ViewCadastro2.TelaCadastro2;
import com.example.varzeando.DetalhesQuadra.DetalhesQuadraController.DetalhesQuadraController;
import com.example.varzeando.Mapa.MapaView.TelaMapaUsuario;
import com.example.varzeando.Models.Quadra;
import com.example.varzeando.R;
import com.example.varzeando.TelaInicial.TelaInicialView.TelaInicialView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class DetalhesQuadra extends AppCompatActivity {

    //ATRIBUTOS DA VIEW
    private TextView nomeQuadra;
    private TextView enderecoQuadra;
    private TextView descricaoQuadra;
    private ImageView imagemQuadraDetalhe;
    private TextView tv_quantidadeAvaliacoes;
    private RatingBar ratingBarEstrelas;
    private FloatingActionButton fab_estrela;
    private ConstraintLayout constraintPrincipal;
    private ConstraintLayout constraintPopUp;
    private RatingBar ratingBarAvaliar;
    private ImageView imageFechar;
    private DetalhesQuadraController detalhesQuadraController;
    private TelaMapaUsuario telaMapaUsuario;
    private Button btn_mapa;

    //ATRIBUTOS QUE VIERAM DO BUNDLE
    private Integer idQuadra;
    private String nome;
    private String descricao;
    private String endereco;
    private String imagem;
    private Float valorRatingBar;
    private Integer totalAvaliacoes;
    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_quadra);
        nomeQuadra = findViewById(R.id.nomeQuadra);
        enderecoQuadra = findViewById(R.id.endereco);
        descricaoQuadra = findViewById(R.id.descQuadra);
        imagemQuadraDetalhe = findViewById(R.id.imagemQuadraDetalhe);
        tv_quantidadeAvaliacoes = findViewById(R.id.qnt_Avaliacoes);
        ratingBarEstrelas = findViewById(R.id.ratingBarEstrelas);
        fab_estrela = findViewById(R.id.floatingActionButtonEstrela);
        ratingBarAvaliar = findViewById(R.id.ratingBarAvaliar);
        imageFechar = findViewById(R.id.imageFechar);
        btn_mapa = findViewById(R.id.buttonVerNoMapa);

        detalhesQuadraController = new DetalhesQuadraController(getApplication());
        telaMapaUsuario = new TelaMapaUsuario();

        constraintPrincipal = findViewById(R.id.ConstraintPrincipal);
        constraintPrincipal.setVisibility(View.VISIBLE);

        constraintPopUp = findViewById(R.id.ConstraintPopUpAvaliar);
        constraintPopUp.setVisibility(View.INVISIBLE);


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            idQuadra = extras.getInt("id");
            nome = extras.getString("nome");
            descricao = extras.getString("descricao");
            endereco = extras.getString("endereco");
            imagem = extras.getString("imagem");
            valorRatingBar = (float)extras.getDouble("media");
            totalAvaliacoes = extras.getInt("quantidade");
            latitude = extras.getDouble("latitude");
            longitude = extras.getDouble("longitude");
        }

        nomeQuadra.setText(nome);
        descricaoQuadra.setText(descricao);
        enderecoQuadra.setText(endereco);
        Glide.with(getApplicationContext()).asBitmap().load(imagem).into(imagemQuadraDetalhe);
        tv_quantidadeAvaliacoes.setText("(" + totalAvaliacoes.toString() + ")");
        ratingBarEstrelas.setRating(valorRatingBar);

        fab_estrela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarPopUpAvaliar(v);
            }
        });

        btn_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TelaMapaUsuario.class);
                intent.putExtra("latitudeMapa", latitude);
                intent.putExtra("longitudeMapa", longitude);
                startActivity(intent);
            }
        });
    }

    public void mostrarPopUpAvaliar(View v){
        //Declaração das variáveis do popUp (os dois botões que o usuário pode escolher)
        Button botaoAvaliar;

        //Localização das widgets do popUp
        botaoAvaliar = findViewById(R.id.btn_avaliar);

        //Deixar o PopUp visível e definir o alpha da constraint principal como 0.5 (deixa com o efeito de desfocado no fundo da PopUp)
        constraintPopUp.setVisibility(View.VISIBLE);
        constraintPrincipal.setAlpha((float)0.4);

        //Definição de um clickListener para o botão de continuar do PopUp (encaminha para a segunda etapa da tela de cadastro)
        botaoAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float getrating = ratingBarAvaliar.getRating();
                //MÉTODO
                if(getrating == 0.0){
                    Toast.makeText(DetalhesQuadra.this, "Insira uma nota para avaliar!", Toast.LENGTH_LONG).show();
                } else{
                    detalhesQuadraController.avaliarQuadra(idQuadra, ratingBarAvaliar.getRating());
                    constraintPrincipal.setAlpha(1);
                    constraintPopUp.setVisibility(View.INVISIBLE);
                }
            }
        });

        imageFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintPrincipal.setAlpha(1);
                constraintPopUp.setVisibility(View.INVISIBLE);
            }
        });
    }
}