package com.example.varzeando.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.varzeando.Models.Confirmados;
import com.example.varzeando.R;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapterConfirmados extends RecyclerView.Adapter<RecyclerViewAdapterConfirmados.ViewHolder> {

    private Context context;
    private List<Confirmados> listaConfirmados;

    public RecyclerViewAdapterConfirmados(Context context, ArrayList<Confirmados> listaConfirmados) {
        this.context = context;
        this.listaConfirmados = listaConfirmados;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_confimados, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).asBitmap().load(listaConfirmados.get(position).getImagemPessoa()).into(holder.imagemPessoa);
        holder.nomePessoa.setText(listaConfirmados.get(position).getNomePessoa());
        holder.posicaoPessoa.setText(listaConfirmados.get(position).getPocicaoPessoa());
        Glide.with(context).asBitmap().load(listaConfirmados.get(position).getImagemEstrela()).into(holder.imagemEstrela);
    }

    @Override
    public int getItemCount() {
        return listaConfirmados.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imagemPessoa;
        TextView nomePessoa;
        TextView posicaoPessoa;
        ImageView imagemEstrela;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemPessoa = itemView.findViewById(R.id.imagemPessoa);
            nomePessoa = itemView.findViewById(R.id.nomePessoa);
            posicaoPessoa = itemView.findViewById(R.id.posicaoPessoa);
            imagemEstrela = itemView.findViewById(R.id.imagemEstrela);
        }

    }
}
