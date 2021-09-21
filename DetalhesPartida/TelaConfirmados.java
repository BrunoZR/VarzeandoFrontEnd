package com.example.varzeando.DetalhesPartida;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.varzeando.Adapters.RecyclerViewAdapterConfirmados;
import com.example.varzeando.Models.Confirmados;
import com.example.varzeando.R;

import java.util.ArrayList;

public class TelaConfirmados extends DialogFragment {

    ArrayList<Confirmados> listaConfirmados;
    RecyclerView recyclerView;
    RecyclerViewAdapterConfirmados recyclerViewAdapterConfirmados;
    int[] imagemPessoa;
    String[] nomePessoa;
    String[] posicaoPessoa;
    int[] imagemEntrela;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_confirmados, container);

        recyclerView = (RecyclerView) view.findViewById(R.id.qt_pessoasConfirmadas);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        listaConfirmados = new ArrayList<Confirmados>();
        recyclerViewAdapterConfirmados = new RecyclerViewAdapterConfirmados(this.getActivity(),listaConfirmados);
        recyclerView.setAdapter(recyclerViewAdapterConfirmados);

        imagemPessoa = new int[]{
                R.drawable.avatar2,
                R.drawable.avatar1,
                R.drawable.avatar2,
                R.drawable.avatar1,
                R.drawable.avatar1,
                R.drawable.avatar1,
                R.drawable.avatar2,
                R.drawable.avatar1
        };

        nomePessoa = new String[]{
                "Ana",
                "Paulo",
                "Maria",
                "Carlos",
                "João",
                "Pedro",
                "Camila",
                "Arthur"

        };

        posicaoPessoa = new String[] {
                "Atacante",
                "Goleiro",
                "Meio",
                "Defesa",
                "Meio",
                "Atacante",
                "Atacante",
                "Defesa"
        };

        imagemEntrela = new int[] {
                R.drawable.ic_estrela,
                R.drawable.ic_estrela,
                R.drawable.ic_estrela,
                R.drawable.ic_estrela,
                R.drawable.ic_estrela,
                R.drawable.ic_estrela,
                R.drawable.ic_estrela,
                R.drawable.ic_estrela
        };

        openPopup();

        getRecyclerViewTodosConfirmados();

        this.getDialog().setTitle("Confirmados");

        return view;
    }

    private void getRecyclerViewTodosConfirmados() {

        for(int i=0; i < nomePessoa.length; i++) {
            Confirmados confirmados = new Confirmados(imagemPessoa[i], nomePessoa[i], posicaoPessoa[i], imagemEntrela[i]);
            listaConfirmados.add(confirmados);
        }
    }

    public void openPopup(){
        final Dialog dialog = getDialog();
        dialog.setContentView(R.layout.fragment_confirmados);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
