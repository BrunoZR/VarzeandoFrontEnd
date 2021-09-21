package com.example.varzeando.ClassesSuporte;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.varzeando.Models.Usuario;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "sessao";
    String SESSION_KEY = "sessao_usuario";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void salvarSessaoUsuario(Usuario usuario){
        Integer id = usuario.getId();
        editor.putInt(SESSION_KEY, id).commit();
    }

    public Integer getIdSession(){
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
    }
}
