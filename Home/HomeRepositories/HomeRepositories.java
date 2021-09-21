package com.example.varzeando.Home.HomeRepositories;

import android.app.Application;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.varzeando.Adapters.RecyclerViewAdapterPartida;
import com.example.varzeando.Adapters.RecyclerViewAdapterPartidasNessaSemana;
import com.example.varzeando.Adapters.RecyclerViewAdapterQuadras;
import com.example.varzeando.Adapters.RecyclerViewAdapterQuadrasProximas;
import com.example.varzeando.ClassesSuporte.SessionManagement;
import com.example.varzeando.ClassesSuporte.UsuarioDAO;
import com.example.varzeando.Mapa.MapaView.TelaMapaUsuario;
import com.example.varzeando.Models.Partida;
import com.example.varzeando.Models.Quadra;
import com.example.varzeando.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeRepositories{
    //Declaração da URL pela qual será acessada a API
    public static final String API_URL = "https://appvarzeando.herokuapp.com/";

    //Declaração das variáveis
    private final Application mAplicattion;
    private RequestQueue mRequestQueue;
    private UsuarioDAO usuarioDAO;
    private SessionManagement sessionManagement;
    private TelaMapaUsuario telaMapaUsuario;

    //Criando uma requestQueue dentro do cosntrutor
    public HomeRepositories(Application application){
        this.mAplicattion = application;
        mRequestQueue = Volley.newRequestQueue(application);
        usuarioDAO = new UsuarioDAO(mAplicattion);
        sessionManagement = new SessionManagement(application);
        telaMapaUsuario = new TelaMapaUsuario();
    }

    public void loadAtributosPartidasNessaSemana(RecyclerViewAdapterPartidasNessaSemana rv){
        String url = API_URL + "partidas/partidasSemana";

        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Partida> partidasSemana = new ArrayList<>();
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
                        partidasSemana.add(partida);
                    }
                    rv.setListaPartidas(partidasSemana);
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

    public void loadAtributosQuadrasProximas(RecyclerViewAdapterQuadrasProximas rv, Double latitude, Double longitude) {
        String url = API_URL + "quadras/quadrasProximas/" + latitude + "/" + longitude;

        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Quadra> quadrasProximas = new ArrayList<>();
                try {
                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        Integer id = jsonObject.getInt("id");
                        String imagemQuadra = jsonObject.getString("url");
                        String nomeQuadra = jsonObject.getString("name");
                        String descricaoQuadra = jsonObject.getString("descricao");
                        String enderecoQuadra = jsonObject.getString("endereco");
                        Double avaliacaoQuadra = jsonObject.getDouble("media");
                        Integer quantidadeAvaliacoes = jsonObject.getInt("quantidade");
                        Double latitude = jsonObject.getDouble("latitude");
                        Double longitude = jsonObject.getDouble("longitude");
                        Quadra quadra = new Quadra(id, nomeQuadra, descricaoQuadra, enderecoQuadra, imagemQuadra, avaliacaoQuadra, quantidadeAvaliacoes, latitude, longitude);
                        quadrasProximas.add(quadra);
                    }
                    rv.setListaQuadrasProximas(quadrasProximas);
                } catch (JSONException e) {
                    Toast.makeText(mAplicattion, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mAplicattion, error.toString(), Toast.LENGTH_SHORT).show();
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

    public void loadAtributosTodasPartidas(RecyclerViewAdapterPartida rv){
        String url = API_URL + "partidas/partidas";

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
