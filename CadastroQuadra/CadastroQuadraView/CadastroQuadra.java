package com.example.varzeando.CadastroQuadra.CadastroQuadraView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.varzeando.Adapters.RecyclerViewAdapterImgsQuadra;
import com.example.varzeando.CadastroQuadra.CadastroQuadraController.CadastroQuadraController;
import com.example.varzeando.Mapa.MapaView.TelaMapaUsuario;
import com.example.varzeando.Models.ImagensQuadra;
import com.example.varzeando.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CadastroQuadra extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private Button buttonAddImg;
    private RecyclerView recyclerViewImgs;
    private List<ImagensQuadra> listaArquivos;
    private RecyclerViewAdapterImgsQuadra recyclerViewAdapterImgsQuadra;
    private EditText nomeQuadra;
    private EditText descricaoQuadra;
    private EditText ruaQuadra;
    private EditText numeroQuadra;
    private EditText bairroQuadra;
    private RatingBar ratingBarAvaliar;
    private Button botaoAdicionarQuadra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_de_quadra);

        nomeQuadra = findViewById(R.id.cd_nomeQuadra);
        descricaoQuadra = findViewById(R.id.cd_descQuadra);
        ruaQuadra = findViewById(R.id.cd_nomeRua);
        numeroQuadra = findViewById(R.id.cd_numRua);
        bairroQuadra = findViewById(R.id.cd_nomeBairro);
        ratingBarAvaliar = findViewById(R.id.ratingBar);
        botaoAdicionarQuadra = findViewById(R.id.buttonAdicionarQuadra);

        buttonAddImg = findViewById(R.id.buttonAddImg);
        recyclerViewImgs = findViewById(R.id.upload_imgs);
        recyclerViewImgs.setHasFixedSize(true);
        recyclerViewImgs.setLayoutManager(new LinearLayoutManager(this));

        listaArquivos = new ArrayList<>();

        buttonAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        botaoAdicionarQuadra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recuperando os dados que foram inseridos nos espaço de texto
                String nome = nomeQuadra.getText().toString();
                String descricao = descricaoQuadra.getText().toString();
                String rua = ruaQuadra.getText().toString();
                String numero = numeroQuadra.getText().toString();
                String bairro = bairroQuadra.getText().toString();
                Float avaliacao = ratingBarAvaliar.getRating();
                String cidade = "São Paulo";

                //Unindo as strings rua, bairro e cidade em uma só chamada endereço para salvar no banco de dados
                String endereco = rua + ", " + numero + ", " + bairro + ", " + cidade;

                LatLng enderecoLatLng = getLocationFromAddress(CadastroQuadra.this, endereco);
                Double latitude = enderecoLatLng.latitude;
                Double longitude = enderecoLatLng.longitude;
                String url = "https://www.topsporteng.com.br/imagens-y/informacoes/quadra-concreto-01.jpg";

                CadastroQuadraController cadastroQuadraController = CadastroQuadraController.getInstance(CadastroQuadra.this.getApplication());
                cadastroQuadraController.adicionarQuadra(nome, descricao, latitude, longitude, endereco, url, avaliacao);
                Intent intent = new Intent(getApplicationContext(), TelaMapaUsuario.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int totalItensSelecionados = data.getClipData().getItemCount();
                for (int i = 0; i < totalItensSelecionados; i++) {
                    Uri imgUri = data.getClipData().getItemAt(i).getUri();
                    String nomeArquivos = getNomeArquivos(imgUri);
                    ImagensQuadra imagensQuadra = new ImagensQuadra(nomeArquivos);
                    listaArquivos.add(imagensQuadra);
                    recyclerViewAdapterImgsQuadra = new RecyclerViewAdapterImgsQuadra(CadastroQuadra.this, listaArquivos);
                    recyclerViewImgs.setAdapter(recyclerViewAdapterImgsQuadra);
                }

            } else if (data.getData() != null) {
                Uri imgUri = data.getData();
                String nomeImg = getNomeArquivos(imgUri);
                ImagensQuadra imagensQuadra = new ImagensQuadra(nomeImg);
                listaArquivos.add(imagensQuadra);
                recyclerViewAdapterImgsQuadra = new RecyclerViewAdapterImgsQuadra(CadastroQuadra.this, listaArquivos);
                recyclerViewImgs.setAdapter(recyclerViewAdapterImgsQuadra);
            }
        }
    }

    public String getNomeArquivos(Uri uri) {
        String resultado = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if(cursor != null && cursor.moveToFirst()){
                    resultado = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (resultado == null) {
            resultado = uri.getPath();
            int cut = resultado.lastIndexOf('/');
            if (cut != -1){
                resultado = resultado.substring(cut + 1);
            }
        }
        return resultado;
    }

    //Método para passar uma string com o endereço e retornar a latitude e a longitude
    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p1;
    }
}




