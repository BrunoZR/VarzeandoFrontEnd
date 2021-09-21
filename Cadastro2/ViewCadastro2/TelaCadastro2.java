package com.example.varzeando.Cadastro2.ViewCadastro2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.varzeando.Cadastro2.Cadastro2Controller.Cadastro2Controller;
import com.example.varzeando.TelaInicial.TelaInicialView.TelaInicialView;
import com.example.varzeando.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class TelaCadastro2 extends AppCompatActivity {

    //Declaração das variáveis do programa
    private Button botaoFinalizar;
    private ImageView imagemPerfil;
    private static final int PICK_IMAGE = 1;
    private Uri imageUri;
    private EditText campoRua;
    private EditText campoNumero;
    private EditText campoBairro;
    private EditText campoCidade;
    private AutoCompleteTextView escolherPosicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro2);

        //Localização das widgets do xml
        escolherPosicao = findViewById(R.id.escolhaPosicaoCadastro);
        botaoFinalizar = findViewById(R.id.btn_finalizarCadastro);
        imagemPerfil = findViewById(R.id.iv_imagemPerfil);
        campoRua = findViewById(R.id.et_ruaCadastro);
        campoNumero = findViewById(R.id.et_numeroResidencia);
        campoBairro = findViewById(R.id.et_bairroCadastro);
        campoCidade = findViewById(R.id.et_cidadeCadastro);

        //Definição de um ArrayAdapter que vai ser responsável por apresentar as opções de posição ao clicar na caixa de escolha
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.posicoes, R.layout.dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        escolherPosicao.setAdapter(adapter);

        //Definição de um clickListener para o botão de finalizar na segunda etapa do cadastro (encaminha para a tela inicial do aplicativo (provisoriamente enquanto não temos uma tela principal))
        botaoFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recuperando os dados que foram inseridos nos espaço de texto
                String rua = campoRua.getText().toString();
                String numero = campoNumero.getText().toString();
                String bairro = campoBairro.getText().toString();
                String cidade = campoCidade.getText().toString();
                String posicao = escolherPosicao.getText().toString();

                //Recuperando o email da etapa 1
                Bundle dados = getIntent().getExtras();
                String email = dados.getString("email");

                //Unindo as strings rua, bairro e cidade em uma só chamada endereço para salvar no banco de dados
                String endereco = rua + ", " + numero + ", " + bairro + ", " + cidade;

                LatLng enderecoLatLng = getLocationFromAddress(TelaCadastro2.this, endereco);
                Double latitude = enderecoLatLng.latitude;
                Double longitude = enderecoLatLng.longitude;

                Cadastro2Controller cadastro2Controller = Cadastro2Controller.getInstance(TelaCadastro2.this.getApplication());
                cadastro2Controller.cadastro2(email, posicao, latitude, longitude);
                Intent intent = new Intent(getApplicationContext(), TelaInicialView.class);
                startActivity(intent);
            }
        });

        //Definição de um clickListener para a imagem de perfil, que ao clicar, será aberta a galeria do usuário e ele poderá escolher uma foto
        imagemPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Escolha uma foto"), PICK_IMAGE);
            }
        });
    }

    //Definição de uma onActivityResult que recupera o bitmap da imagem para mudar no aplicativo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Salva os dados da imagem utilizando um try e catch
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imagemPerfil.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //Método para passar uma string com o endereço e retornar a latitude e a longitude
    public LatLng getLocationFromAddress(Context context,String strAddress) {
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