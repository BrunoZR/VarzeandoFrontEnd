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
import com.example.varzeando.Models.Partida;
import com.example.varzeando.R;

import java.util.ArrayList;

public class RecyclerViewAdapterPartidasNessaSemana extends RecyclerView.Adapter<RecyclerViewAdapterPartidasNessaSemana.ViewHolder> {

    private Context context;
    public ArrayList<Partida> listaPartidasProximas = new ArrayList<>();
    private RecyclerViewAdapterPartidasNessaSemana.RecyclerViewClickListener listener;

    public RecyclerViewAdapterPartidasNessaSemana(Context context, RecyclerViewAdapterPartidasNessaSemana.RecyclerViewClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterPartidasNessaSemana.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_partidas, parent, false);
        return new RecyclerViewAdapterPartidasNessaSemana.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterPartidasNessaSemana.ViewHolder holder, int position) {
        Glide.with(context).asBitmap().load(listaPartidasProximas.get(position).getImagemPartida()).into(holder.imagemPartida);
        holder.nomePartida.setText(listaPartidasProximas.get(position).getNomePartida());
        holder.enderecoPartida.setText(listaPartidasProximas.get(position).getEnderecoPartida());
        holder.dataPartida.setText(listaPartidasProximas.get(position).getDataPartida());
        holder.horaPartida.setText(listaPartidasProximas.get(position).getHoraPartida());
        holder.numeroPessoasPartida.setText(listaPartidasProximas.get(position).getNumeroPessoas().toString());
    }

    @Override
    public int getItemCount() {
        return listaPartidasProximas.size();
    }

    public void setListaPartidas(ArrayList<Partida> partidas){
        this.listaPartidasProximas = partidas;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imagemPartida;
        TextView nomePartida;
        TextView enderecoPartida;
        TextView dataPartida;
        TextView horaPartida;
        TextView numeroPessoasPartida;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemPartida = itemView.findViewById(R.id.imagemPartida);
            nomePartida = itemView.findViewById(R.id.nomePartida);
            enderecoPartida = itemView.findViewById(R.id.enderecoPartida);
            dataPartida = itemView.findViewById(R.id.dataPartida);
            horaPartida = itemView.findViewById(R.id.horaPartida);
            numeroPessoasPartida = itemView.findViewById(R.id.numeroPessoasPartida);
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
