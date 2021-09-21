package com.example.varzeando.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.varzeando.Models.Partida;
import com.example.varzeando.Models.Quadra;
import com.example.varzeando.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapterPartida extends RecyclerView.Adapter<RecyclerViewAdapterPartida.ViewHolder> implements Filterable {

    private Context context;
    public ArrayList<Partida> listaPartidas = new ArrayList<>();
    private ArrayList<Partida> listaPartidasTodas;
    private RecyclerViewAdapterPartida.RecyclerViewClickListener listener;

    public RecyclerViewAdapterPartida(Context context, RecyclerViewAdapterPartida.RecyclerViewClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_partidas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).asBitmap().load(listaPartidas.get(position).getImagemPartida()).into(holder.imagemPartida);
        holder.nomePartida.setText(listaPartidas.get(position).getNomePartida());
        holder.enderecoPartida.setText(listaPartidas.get(position).getEnderecoPartida());
        holder.dataPartida.setText(listaPartidas.get(position).getDataPartida());
        holder.horaPartida.setText(listaPartidas.get(position).getHoraPartida());
        holder.numeroPessoasPartida.setText(listaPartidas.get(position).getNumeroPessoas().toString());
    }

    @Override
    public int getItemCount() {
        return listaPartidas.size();
    }

    public void setListaPartidas(ArrayList<Partida> partidas){
        this.listaPartidas = partidas;
        this.listaPartidasTodas = new ArrayList<>(partidas);
        this.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Partida> listaFiltrada = new ArrayList<>();
            if(constraint.toString().isEmpty()){
                listaFiltrada.addAll(listaPartidasTodas);
            } else{
                for(Partida partida : listaPartidasTodas){
                    if(partida.getNomePartida().toLowerCase().contains(constraint.toString().toLowerCase())){
                        listaFiltrada.add(partida);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = listaFiltrada;
            return filterResults;
        }

        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaPartidas.clear();
            listaPartidas.addAll((Collection<? extends Partida>) results.values);
            notifyDataSetChanged();
        }
    };

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





