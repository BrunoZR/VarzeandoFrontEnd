package com.example.varzeando.Perfil.PerfilView;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.varzeando.Adapters.RecyclerViewAdapterPartida;
import com.example.varzeando.ClassesSuporte.SessionManagement;
import com.example.varzeando.ClassesSuporte.UsuarioDAO;
import com.example.varzeando.DetalhesQuadra.DetalhesQuadraView.DetalhesQuadra;
import com.example.varzeando.Perfil.PerfilController.PerfilController;
import com.example.varzeando.R;
import com.example.varzeando.TelaInicial.TelaInicialView.TelaInicialView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TelaPerfilFragment extends Fragment {
    private RecyclerView recyclerViewPartidas;
    private Application application;
    private Context context;
    private PerfilController perfilController;
    private SessionManagement sessionManagement;
    private Integer userId;
    private String nome;
    private UsuarioDAO usuarioDAO;
    private TextView tv_nomeUsuario;

    //Declaração das variáveis da view
    private RecyclerViewAdapterPartida recyclerViewAdapterPartida;
    private RecyclerViewAdapterPartida.RecyclerViewClickListener listenerPartidas;
    private FloatingActionButton fab_sair;

    public TelaPerfilFragment(Application application, Context context) {
        this.application = application;
        this.context = context;
        perfilController = new PerfilController(application);
        usuarioDAO = new UsuarioDAO(context);
        sessionManagement = new SessionManagement(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tela_perfil, container, false);

        //PROCURANDO WIDGETS XML
        recyclerViewPartidas = view.findViewById(R.id.rv_suasPartidas);
        tv_nomeUsuario = view.findViewById(R.id.tv_nomeUsuario);
        fab_sair = view.findViewById(R.id.floatingActionButtonLogOut);

        fab_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManagement.removeSession();
                Intent intent = new Intent(context, TelaInicialView.class);
                startActivity(intent);
            }
        });

        //VALORES PARA AS VARIÁVEIS
        userId = sessionManagement.getIdSession();
        nome = usuarioDAO.recuperarNome(userId);

        //DECLARAR NOME
        tv_nomeUsuario.setText(nome);

        //INICIALIZAR O RECYCLER VIEW DE PARTIDA
        initRecyclerViewPartidas();

        return view;
    }

    private void initRecyclerViewPartidas(){
        setOnClickListener();
        LinearLayoutManager layoutManager = new LinearLayoutManager(application, LinearLayoutManager.VERTICAL, false);
        recyclerViewPartidas.setLayoutManager(layoutManager);
        recyclerViewAdapterPartida = new RecyclerViewAdapterPartida(application, listenerPartidas);
        recyclerViewPartidas.setAdapter(recyclerViewAdapterPartida);
        perfilController.loadTodasPartidas(recyclerViewAdapterPartida);
    }

    private void setOnClickListener(){
        listenerPartidas = new RecyclerViewAdapterPartida.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(application, DetalhesQuadra.class);
                intent.putExtra("nomePartida", recyclerViewAdapterPartida.listaPartidas.get(position).getNomePartida());
                intent.putExtra("imagemPartida", recyclerViewAdapterPartida.listaPartidas.get(position).getImagemPartida());
                intent.putExtra("id", recyclerViewAdapterPartida.listaPartidas.get(position).getId());
                intent.putExtra("enderecoPartida", recyclerViewAdapterPartida.listaPartidas.get(position).getDescricaoPartida());
                intent.putExtra("dataInicioPartida", recyclerViewAdapterPartida.listaPartidas.get(position).getEnderecoPartida());
                intent.putExtra("horarioPartida", recyclerViewAdapterPartida.listaPartidas.get(position).getHorarioPartida());
                intent.putExtra("numeroPessoasPartida", recyclerViewAdapterPartida.listaPartidas.get(position).getNumeroPessoas());
                intent.putExtra("descricaoPartida", recyclerViewAdapterPartida.listaPartidas.get(position).getDescricaoPartida());
                startActivity(intent);
            }
        };
    }
}