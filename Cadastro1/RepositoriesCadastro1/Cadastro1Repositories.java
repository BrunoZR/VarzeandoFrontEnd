package com.example.varzeando.Cadastro1.RepositoriesCadastro1;

import android.app.Application;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.varzeando.ClassesSuporte.SessionManagement;
import com.example.varzeando.ClassesSuporte.UsuarioDAO;
import com.example.varzeando.Models.Usuario;
import com.example.varzeando.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Cadastro1Repositories {
    //Declaração da URL pela qual será acessada a API
    public static final String API_URL = "https://appvarzeando.herokuapp.com/";

    //Declaração das variáveis
    private final Application mAplicattion;
    private RequestQueue mRequestQueue;
    private UsuarioDAO usuarioDAO;
    private SessionManagement sessionManagement;

    //Criando uma requestQueue dentro do cosntrutor
    public Cadastro1Repositories(Application application){
        mRequestQueue = Volley.newRequestQueue(application);
        mAplicattion = application;
        usuarioDAO = new UsuarioDAO(application);
        sessionManagement = new SessionManagement(application);
    }

    //Criação do método cadastro afim de passar para a API os dados da primeira etapa do cadastro
    public void cadastro1(String nome, String dataNascimento, String email, String senha){
        //Declaração da url específica de login que utiliza a URL base junto com a terminação
        String url = API_URL + "usuarios/cadastro";

        //Criação de um JSONObject para passar as informações para a API
        JSONObject jsonObject = new JSONObject();

        //Uso de try e catch para passar as informações para API
        try {
            jsonObject.put("name", nome);
            jsonObject.put("dataNascimento", dataNascimento);
            jsonObject.put("email", email);
            jsonObject.put("password", senha);

            //Criação de um responseListener responsável por avisar se deu certo a ligação
            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject jasonObjectResponse = response;
                        Integer id = jasonObjectResponse.getInt("id");
                        String token = jasonObjectResponse.getString("token");
                        String nome = jasonObjectResponse.getString("name");
                        Usuario usuario = new Usuario(id, nome, token);
                        usuarioDAO.salvar(usuario);
                        sessionManagement.salvarSessaoUsuario(usuario);
                        Toast.makeText(mAplicattion, R.string.primeira_etapa_concluida, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(mAplicattion, R.string.erro_json, Toast.LENGTH_SHORT).show();
                    }
                }
            };

            //Criação de um errorListener responsável por avisar se deu errado a ligação
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mAplicattion, R.string.primeira_etapa_errada, Toast.LENGTH_SHORT).show();
                }
            };

            //Criação de um JsonObjectRequest que passara todos os dados necessários para a api
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, responseListener, errorListener);

            //Adicionar essa requisição para a fila de requisição
            mRequestQueue.add(request);
        } catch (JSONException e) {
            Toast.makeText(mAplicattion, R.string.erro_json, Toast.LENGTH_SHORT).show();
        }
    }
}
