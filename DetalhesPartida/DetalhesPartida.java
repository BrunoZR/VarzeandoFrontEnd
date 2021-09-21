package com.example.varzeando.DetalhesPartida;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.varzeando.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

public class DetalhesPartida extends AppCompatActivity {

    //ATRIBUTOS DA VIEW
    private TextView nomePartida;
    private TextView dataPartida;
    private TextView numPessoasPartida;
    private TextView enderecoPartida;
    private TextView expandable_text;
    private ImageView imagemPartida;
    private CardView confirmados;
    Dialog myDialog;

    //ATRIBUTOS QUE VIERAM DO BUNDLE
    private String nome;
    private String data;
    private Integer numPessoas;
    private String imagem;
    private String endereco;
    private String descricao;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_detalhes_partida);

        //EXPANDIR O TEXTO A MAIS
        myDialog = new Dialog(this);
        nomePartida = findViewById(R.id.nomeQuadra);
        dataPartida = findViewById(R.id.endereco);
        numPessoasPartida = findViewById(R.id.descQuadra);
        enderecoPartida = findViewById(R.id.enderecoPartida);
        imagemPartida = findViewById(R.id.imagemPartida);
        ExpandableTextView textoExpandido = (ExpandableTextView)findViewById(R.id.expandirTexto);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            nome = extras.getString("nomePartida");
            imagem = extras.getString("imagemPartida");
            id = extras.getInt("id");
            endereco = extras.getString("enderecoPartida");
            data = extras.getString("dataInicioPartida");
            descricao = extras.getString("descricaoPartida");
            numPessoas = extras.getInt("numeroPessoasPartida");
        }
//        Glide.with(getApplicationContext()).asBitmap().load(imagem).into(imagemPartida);
//        nomePartida.setText(nome);
//        dataPartida.setText(data);
//        numPessoasPartida.setText(numPessoas);
//        enderecoPartida.setText(endereco);
//        textoExpandido.setText(descricao);

        confirmados = findViewById(R.id.cardView2);

        final FragmentManager fm = getSupportFragmentManager();
        final TelaConfirmados tc = new TelaConfirmados();

        confirmados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tc.show(fm, "Confirmados");
            }
        });
    }
}
