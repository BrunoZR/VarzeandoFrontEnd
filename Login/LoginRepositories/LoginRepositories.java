package com.example.varzeando.Login.LoginRepositories;

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
import com.example.varzeando.ClassesSuporte.UsuarioDAO;
import com.example.varzeando.R;
import com.example.varzeando.ClassesSuporte.SessionManagement;
import com.example.varzeando.Models.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginRepositories{

    public static final String API_URL = "https://appvarzeando.herokuapp.com/";
    private RequestQueue mRequestQueue;
    private Application mApplication;
    private UsuarioDAO usuarioDAO;
    private SessionManagement sessionManagement;

    public LoginRepositories(Application application){
        this.mApplication = application;
        mRequestQueue = Volley.newRequestQueue(application);
        usuarioDAO = new UsuarioDAO(mApplication);
        sessionManagement = new SessionManagement(application);
    }

    public void login(String email, String senha){
        String url = API_URL + "usuarios/login";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email", email);
            jsonObject.put("password", senha);

            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject jasonObjectResponse = response;
                        Integer id = jasonObjectResponse.getInt("id");
                        String token = jasonObjectResponse.getString("token");
//                        String nome = jasonObjectResponse.getString("nome");
                        Usuario usuario = new Usuario(id);
                        sessionManagement.salvarSessaoUsuario(usuario);
                        Log.i("CADASTRO", sessionManagement.getIdSession().toString());
                        Toast.makeText(mApplication, R.string.login_com_sucesso, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mApplication, R.string.credenciais_erradas, Toast.LENGTH_SHORT).show();
                }
            };
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, responseListener, errorListener);
            mRequestQueue.add(request);
        } catch (JSONException e) {
            Toast.makeText(mApplication, R.string.erro_json, Toast.LENGTH_SHORT).show();
        }
    }
}
