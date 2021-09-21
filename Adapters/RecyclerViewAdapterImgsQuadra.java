package com.example.varzeando.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.varzeando.Models.ImagensQuadra;
import com.example.varzeando.R;

import java.util.List;

public class RecyclerViewAdapterImgsQuadra extends RecyclerView.Adapter<RecyclerViewAdapterImgsQuadra.ViewHolder> {

    Context context;
    List<ImagensQuadra> listaImgQuadra;

    public RecyclerViewAdapterImgsQuadra(Context context, List<ImagensQuadra> listaImgQuadra) {
        this.context = context;
        this.listaImgQuadra = listaImgQuadra;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lista_imagens_quadra, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.nomeImg.setText(listaImgQuadra.get(position).getNomeImagem());
    }

    @Override
    public int getItemCount() {
        return listaImgQuadra.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nomeImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeImg = itemView.findViewById(R.id.cd_nomeQuadra);
        }

    }

}
