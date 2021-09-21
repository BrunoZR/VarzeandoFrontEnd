package com.example.varzeando.ClassesSuporte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.varzeando.Models.Usuario;

public class UsuarioDAO implements IUsuarioDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public UsuarioDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Usuario usuario) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", usuario.getId());
        contentValues.put("nome", usuario.getNome());
        contentValues.put("token", usuario.getToken());

        try {
            escreve.insert(DbHelper.TABELA_USUARIOS, null, contentValues);
            Log.i("INFO", "Usuario salvo com sucesso!");
        } catch (Exception e){
            Log.e("INFO", "Erro ao salvar tarefa " + e.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    public String recuperarNome(int id) {
        String nome = "";

        String sql = "SELECT * FROM " + DbHelper.TABELA_USUARIOS + " WHERE id == " + id + " ;";
        Log.i("SQL:", sql);
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){
            nome = c.getString( c.getColumnIndex("nome"));
        }
        return nome;
    }

    @Override
    public String recuperarToken(int id) {
        String token = "";

        String sql = "SELECT * FROM " + DbHelper.TABELA_USUARIOS + " WHERE id == " + id + " ;";
        Log.i("SQL:", sql);
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){
            token = c.getString( c.getColumnIndex("token") );
        }
        return token;
    }
}
