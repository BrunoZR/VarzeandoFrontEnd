package com.example.varzeando.CadastroQuadra.CadastroQuadraRepositories;

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

public class CadastroQuadraRepositories {
    //Declaração da URL pela qual será acessada a API
    public static final String API_URL = "https://appvarzeando.herokuapp.com/";

    //Declaração das variáveis
    private final Application mAplicattion;
    private RequestQueue mRequestQueue;
    private UsuarioDAO usuarioDAO;
    private SessionManagement sessionManagement;

    //Criando uma requestQueue dentro do cosntrutor
    public CadastroQuadraRepositories(Application application){
        mRequestQueue = Volley.newRequestQueue(application);
        mAplicattion = application;
        usuarioDAO = new UsuarioDAO(mAplicattion);
        sessionManagement = new SessionManagement(application);
    }

    public void adicionarQuadra(String nomeQuadra, String descricaoQuadra, Double latitude, Double longitude, String endereco, String urlQuadra, Float avaliacao) {
        //Declaração da url específica de login que utiliza a URL base junto com a terminação
        String url = API_URL + "quadras/cadastro";

        //Criação de um JSONObject para passar as informações para a API
        JSONObject jsonObject = new JSONObject();

        //Uso de try e catch para passar as informações para a API
        try {
            //Criação de um jsonObject com o email e a senha
            jsonObject.put("name", nomeQuadra);
            jsonObject.put("descricao", descricaoQuadra);
            jsonObject.put("latitude", latitude);
            jsonObject.put("longitude", longitude);
            jsonObject.put("endereco", endereco);
            jsonObject.put("url", urlQuadra);

            //Criação de um responseListener responsável por avisar se deu certo a ligação
            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(mAplicattion, "Quadra cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jasonObjectResponse = response;
                        Integer id = jasonObjectResponse.getInt("id");
                        avaliarQuadra(id, avaliacao);
                    } catch (JSONException e) {
                        Toast.makeText(mAplicattion, "Ocorreu erro na avaliação", Toast.LENGTH_SHORT).show();
                    }
                }
            };

            //Criação de um errorListener responsável por avisar se deu errado a ligação
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mAplicattion, error.toString(), Toast.LENGTH_SHORT).show();
                }
            };

            //Criação de um JsonObjectRequest que passara todos os dados necessários para a api
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, responseListener, errorListener)
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
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

    public void avaliarQuadra(Integer idQuadra, Float avaliacao){
        String url = API_URL + "quadras/avaliarQuadra";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("idQuadra", idQuadra);
            jsonObject.put("idUsuario", sessionManagement.getIdSession());
            jsonObject.put("nota", avaliacao);

            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(mAplicattion, "Quadra avaliada com sucesso!", Toast.LENGTH_SHORT).show();
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mAplicattion, error.toString(), Toast.LENGTH_SHORT).show();
                }
            };
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonObject, responseListener, errorListener)
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + usuarioDAO.recuperarToken(sessionManagement.getIdSession()));
                    return headers;
                }
            };
            mRequestQueue.add(request);
        } catch (JSONException e) {
            Toast.makeText(mAplicattion, R.string.erro_json, Toast.LENGTH_SHORT).show();
        }
    }
}
