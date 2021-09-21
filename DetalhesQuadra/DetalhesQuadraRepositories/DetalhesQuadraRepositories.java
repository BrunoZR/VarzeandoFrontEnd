package com.example.varzeando.DetalhesQuadra.DetalhesQuadraRepositories;

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
import com.example.varzeando.Models.Usuario;
import com.example.varzeando.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetalhesQuadraRepositories {

    public static final String API_URL = "https://appvarzeando.herokuapp.com/";
    private RequestQueue mRequestQueue;
    private Application mApplication;
    private UsuarioDAO usuarioDAO;
    private SessionManagement sessionManagement;

    public DetalhesQuadraRepositories(Application application){
        this.mApplication = application;
        mRequestQueue = Volley.newRequestQueue(application);
        usuarioDAO = new UsuarioDAO(mApplication);
        sessionManagement = new SessionManagement(application);
    }

    public void avaliarQuadra(Integer idQuadra, Float avaliacao){
        String url = API_URL + "quadras/avaliarQuadra";
        JSONObject jsonObject = new JSONObject();

        try {
            Toast.makeText(mApplication, idQuadra.toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(mApplication, sessionManagement.getIdSession().toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(mApplication, avaliacao.toString(), Toast.LENGTH_SHORT).show();
            Log.i("token:", usuarioDAO.recuperarToken(sessionManagement.getIdSession()));
            jsonObject.put("idQuadra", idQuadra);
            jsonObject.put("idUsuario", sessionManagement.getIdSession());
            jsonObject.put("nota", avaliacao);

            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(mApplication, "Quadra avaliada com sucesso!", Toast.LENGTH_SHORT).show();
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mApplication, error.toString(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(mApplication, R.string.erro_json, Toast.LENGTH_SHORT).show();
        }
    }
}
