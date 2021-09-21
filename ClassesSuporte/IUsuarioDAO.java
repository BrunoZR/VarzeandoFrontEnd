package com.example.varzeando.ClassesSuporte;

import com.example.varzeando.Models.Usuario;

public interface IUsuarioDAO {
    public boolean salvar(Usuario usuario);
    public String recuperarNome(int id);
    public String recuperarToken(int id);
}
