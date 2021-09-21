package com.example.varzeando.Cadastro2.Cadastro2Repositories;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.varzeando.ClassesSuporte.SessionManagement;
import com.example.varzeando.ClassesSuporte.UsuarioDAO;
import com.example.varzeando.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Cadastro2Repositories {
    //Declaração da URL pela qual será acessada a API
    public static final String API_URL = "https://appvarzeando.herokuapp.com/";

    //Declaração das variáveis
    private final Application mAplicattion;
    private RequestQueue mRequestQueue;
    private UsuarioDAO usuarioDAO;
    private SessionManagement sessionManagement;

    //Criando uma requestQueue dentro do cosntrutor
    public Cadastro2Repositories(Application application){
        mRequestQueue = Volley.newRequestQueue(application);
        mAplicattion = application;
        usuarioDAO = new UsuarioDAO(mAplicattion);
        sessionManagement = new SessionManagement(application);
    }

    public void cadastro2(String email, String posicao, Double latitude, Double longitude) {
        //Declaração da url específica de login que utiliza a URL base junto com a terminação
        String url = API_URL + "usuarios/segundocadastro";

        //Criação de um JSONObject para passar as informações para a API
        JSONObject jsonObject = new JSONObject();

        //Uso de try e catch para passar as informações para a API
        try {
            //Criação de um jsonObject com o email e a senha
            jsonObject.put("email", email);
            jsonObject.put("latitude", latitude);
            jsonObject.put("longitude", longitude);
            jsonObject.put("posicao", posicao);
            Log.i("CADASTRO:", jsonObject.toString());

            //Criação de um responseListener responsável por avisar se deu certo a ligação
            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(mAplicattion, R.string.segunda_etapa_concluida, Toast.LENGTH_SHORT).show();
                }
            };

            //Criação de um errorListener responsável por avisar se deu errado a ligação
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mAplicattion, R.string.segunda_etapa_errada, Toast.LENGTH_SHORT).show();
                }
            };

            //Criação de um JsonObjectRequest que passara todos os dados necessários para a api
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonObject, responseListener, errorListener)
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    Log.i("CADASTRO TOKEN", usuarioDAO.recuperarToken(sessionManagement.getIdSession()));
                    headers.put("Authorization", "Bearer " + usuarioDAO.recuperarToken(sessionManagement.getIdSession()));
                    return headers;
                }
            };

            //Adicionar essa requisição para a fila de requisição
            mRequestQueue.add(request);
        } catch (JSONException e) {
            Toast.makeText(mAplicattion, R.string.erro_json, Toast.LENGTH_SHORT).show();
        }
    }
}
