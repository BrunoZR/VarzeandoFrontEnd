package com.example.varzeando.Perfil.PerfilRepositories;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.varzeando.Adapters.RecyclerViewAdapterPartida;
import com.example.varzeando.ClassesSuporte.SessionManagement;
import com.example.varzeando.ClassesSuporte.UsuarioDAO;
import com.example.varzeando.Models.Partida;
import com.example.varzeando.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PerfilRepositories {
    //Declaração da URL pela qual será acessada a API
    public static final String API_URL = "https://appvarzeando.herokuapp.com/";

    //Declaração das variáveis
    private final Application mAplicattion;
    private RequestQueue mRequestQueue;
    private UsuarioDAO usuarioDAO;
    private SessionManagement sessionManagement;

    //Criando uma requestQueue dentro do cosntrutor
    public PerfilRepositories(Application application){
        this.mAplicattion = application;
        mRequestQueue = Volley.newRequestQueue(application);
        usuarioDAO = new UsuarioDAO(mAplicattion);
        sessionManagement = new SessionManagement(application);
    }

    public void loadAtributosTodasPartidas(RecyclerViewAdapterPartida rv){
        String url = API_URL + "usuarios/partidasUsuario/" + sessionManagement.getIdSession();

        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Partida> todasPartidas = new ArrayList<>();
                try {
                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        Integer id = jsonObject.getInt("id");
                        String imagemPartida = jsonObject.getString("url");
                        String nomePartida = jsonObject.getString("name");
                        String descricaoPartida = jsonObject.getString("descricao");
                        Integer numeroPessoasPartida = jsonObject.getInt("numPessoas");
                        String dataInicioPartida = jsonObject.getString("dataInicio");
                        JSONObject jsonObjectQuadra = jsonObject.getJSONObject("quadra");
                        String enderecoPartida = jsonObjectQuadra.getString("endereco");
                        Partida partida = new Partida(id, imagemPartida, nomePartida, enderecoPartida, dataInicioPartida, numeroPessoasPartida, descricaoPartida);
                        todasPartidas.add(partida);
                    }
                    rv.setListaPartidas(todasPartidas);
                } catch (JSONException e) {
                    Toast.makeText(mAplicattion, R.string.erro_json, Toast.LENGTH_SHORT).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mAplicattion, R.string.erro_json, Toast.LENGTH_SHORT).show();
            }
        };
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, responseListener, errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + usuarioDAO.recuperarToken(sessionManagement.getIdSession()));
                return headers;
            }
        };
        mRequestQueue.add(jsonArrayRequest);
    }
}
