package com.example.varzeando.Home.HomeView;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.varzeando.Adapters.RecyclerViewAdapterPartida;
import com.example.varzeando.Adapters.RecyclerViewAdapterPartidasNessaSemana;
import com.example.varzeando.Adapters.RecyclerViewAdapterQuadras;
import com.example.varzeando.Adapters.RecyclerViewAdapterQuadrasProximas;
import com.example.varzeando.ClassesSuporte.SessionManagement;
import com.example.varzeando.ClassesSuporte.UsuarioDAO;
import com.example.varzeando.DetalhesPartida.DetalhesPartida;
import com.example.varzeando.DetalhesQuadra.DetalhesQuadraView.DetalhesQuadra;
import com.example.varzeando.Home.HomeController.HomeController;
import com.example.varzeando.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TelaHomeFragment extends Fragment {
    //Declaração das variáveis necessárias do programa
    private Application application;
    private Context context;
    private HomeController homeController;
    private SessionManagement sessionManagement;
    private Integer userId;
    private String nome;
    private UsuarioDAO usuarioDAO;
    private Double latitude;
    private Double longitude;
    private BottomAppBar bottomAppBar;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton floatingActionButton;

    //Declaração das variáveis da view
    private TextView tv_nomeUsuario;
    private SearchView sv_buscarPartidas;

    //RECYCLER VIEW TODAS QUADRAS
    private RecyclerView recyclerViewTodasQuadras;
    private RecyclerView recyclerViewTodasPartidas;
    private RecyclerView recyclerViewPartidasNessaSemana;
    private RecyclerViewAdapterPartida recyclerViewAdapterPartida;
    private RecyclerViewAdapterPartidasNessaSemana recyclerViewAdapterPartidasNessaSemana;
    private RecyclerViewAdapterPartida.RecyclerViewClickListener listenerPartidas;

    private RecyclerViewAdapterQuadras.RecyclerViewClickListener listener;
    private RecyclerViewAdapterPartida.RecyclerViewClickListener listenerPartida;
    private RecyclerViewAdapterPartidasNessaSemana.RecyclerViewClickListener listenerPartidasEssaSemana;

    //RECYCLER VIEW QUADRAS PROXIMAS
    private RecyclerView recyclerViewQuadrasProximas;
    private RecyclerViewAdapterQuadrasProximas recyclerViewAdapterQuadrasProximas;

    public TelaHomeFragment(Application application, Context context) {
        this.application = application;
        this.context = context;
        homeController = new HomeController(application);
        usuarioDAO = new UsuarioDAO(context);
        sessionManagement = new SessionManagement(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tela_home, container, false);

        //Procurando as widgets no XML
        tv_nomeUsuario = view.findViewById(R.id.tv_nomeUsuario);
        sv_buscarPartidas = view.findViewById(R.id.sv_buscarPartidas);
        bottomAppBar = getActivity().findViewById(R.id.bottomAppBar);
        floatingActionButton = getActivity().findViewById(R.id.fabMapa);
        latitude = -23.498221;
        longitude = -46.693345;

        sv_buscarPartidas.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomAppBar.setVisibility(View.INVISIBLE);
                floatingActionButton.setVisibility(View.INVISIBLE);
            }
        });

        sv_buscarPartidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv_buscarPartidas.setIconified(false);
            }
        });

        sv_buscarPartidas.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                bottomAppBar.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.VISIBLE);
                return false;
            }
        });

        //Configurando o sistema de busca de quadras pelo nome
        sv_buscarPartidas.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bottomAppBar.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapterPartida.getFilter().filter(newText);
                return false;
            }
        });

        //Recycler views
        recyclerViewTodasPartidas = view.findViewById(R.id.rv_TodasPartidas);
        recyclerViewPartidasNessaSemana = view.findViewById(R.id.rv_partidasFiltro);

        //Declarando o nome do usuário pelo BD local
        userId = sessionManagement.getIdSession();
        nome = usuarioDAO.recuperarNome(userId);
        tv_nomeUsuario.setText(nome);

        //Inicializar os recycler view das quadras
        initRecyclerViewTodasPartidas();

        //Retornando a view
        return view;
    }

    private void initRecyclerViewTodasPartidas() {
//        setOnClickListener();
        LinearLayoutManager layoutManager = new LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTodasPartidas.setLayoutManager(layoutManager);
        recyclerViewAdapterPartida = new RecyclerViewAdapterPartida(application, listenerPartida);
        recyclerViewTodasPartidas.setAdapter(recyclerViewAdapterPartida);
        homeController.loadTodasPartidas(recyclerViewAdapterPartida);
    }

    private void initRecyclerViewPartidasEssaSemana(){
//        setOnClickListener();
        LinearLayoutManager layoutManager = new LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPartidasNessaSemana.setLayoutManager(layoutManager);
        recyclerViewAdapterPartidasNessaSemana = new RecyclerViewAdapterPartidasNessaSemana(application, listenerPartidasEssaSemana);
        recyclerViewTodasPartidas.setAdapter(recyclerViewAdapterPartidasNessaSemana);
        homeController.loadPartidasNessaSemana(recyclerViewAdapterPartidasNessaSemana);
    }

//    private void setOnClickListener(){
//        listener = new RecyclerViewAdapterQuadras.RecyclerViewClickListener() {
//            @Override
//            public void onClick(View v, int position) {
//                Intent intent = new Intent(application, DetalhesQuadra.class);
//                intent.putExtra("nome", recyclerViewAdapterTodasQuadras.listaQuadras.get(position).getNomeQuadra());
//                intent.putExtra("id", recyclerViewAdapterTodasQuadras.listaQuadras.get(position).getId());
//                intent.putExtra("descricao", recyclerViewAdapterTodasQuadras.listaQuadras.get(position).getDescricaoQuadra());
//                intent.putExtra("endereco", recyclerViewAdapterTodasQuadras.listaQuadras.get(position).getEnderecoQuadra());
//                intent.putExtra("imagem", recyclerViewAdapterTodasQuadras.listaQuadras.get(position).getImagemQuadra());
//                intent.putExtra("media", recyclerViewAdapterTodasQuadras.listaQuadras.get(position).getAvaliacao());
//                intent.putExtra("quantidade", recyclerViewAdapterTodasQuadras.listaQuadras.get(position).getQuantidade());
//                startActivity(intent);
//            }
//        };
//    }
}