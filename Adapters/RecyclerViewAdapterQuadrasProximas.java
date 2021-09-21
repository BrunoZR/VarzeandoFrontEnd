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
import com.example.varzeando.Models.Quadra;
import com.example.varzeando.R;

import java.util.ArrayList;

public class RecyclerViewAdapterQuadrasProximas extends RecyclerView.Adapter<RecyclerViewAdapterQuadrasProximas.ViewHolder> {

    //Armazenar a lista de quadras
    private Context mContext;
    public ArrayList<Quadra> listaQuadrasProximas = new ArrayList<>();
    private RecyclerViewAdapterQuadras.RecyclerViewClickListener listener;

    public RecyclerViewAdapterQuadrasProximas(Context mContext, RecyclerViewAdapterQuadras.RecyclerViewClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterQuadrasProximas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new RecyclerViewAdapterQuadrasProximas.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterQuadrasProximas.ViewHolder holder, int position) {
        Glide.with(mContext).asBitmap().load(listaQuadrasProximas.get(position).getImagemQuadra()).into(holder.imagemQuadra);
        holder.nomeQuadra.setText(listaQuadrasProximas.get(position).getNomeQuadra());
        holder.enderecoQuadra.setText(listaQuadrasProximas.get(position).getEnderecoQuadra());
    }

    @Override
    public int getItemCount() {
        return listaQuadrasProximas.size();
    }

    public void setListaQuadrasProximas(ArrayList<Quadra> quadras){
        this.listaQuadrasProximas = quadras;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imagemQuadra;
        TextView nomeQuadra;
        TextView enderecoQuadra;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemQuadra = itemView.findViewById(R.id.imagemQuadra);
            nomeQuadra = itemView.findViewById(R.id.textQuadra);
            enderecoQuadra = itemView.findViewById(R.id.enderecoQuadra);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getBindingAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
