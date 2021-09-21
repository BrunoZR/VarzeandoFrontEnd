package com.example.varzeando.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.varzeando.Home.HomeView.TelaHomeFragment;
import com.example.varzeando.R;
import com.example.varzeando.Models.Quadra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerViewAdapterQuadras extends RecyclerView.Adapter<RecyclerViewAdapterQuadras.ViewHolder> implements Filterable {

    //Armazenar a lista de quadras
    private Context mContext;
    public ArrayList<Quadra> listaQuadras = new ArrayList<>();
    private ArrayList<Quadra> listaQuadrasTodas;
    private RecyclerViewClickListener listener;

    public RecyclerViewAdapterQuadras(Context mContext, RecyclerViewClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).asBitmap().load(listaQuadras.get(position).getImagemQuadra()).into(holder.imagemQuadra);
        holder.nomeQuadra.setText(listaQuadras.get(position).getNomeQuadra());
        holder.enderecoQuadra.setText(listaQuadras.get(position).getEnderecoQuadra());
    }

    @Override
    public int getItemCount() {
        return listaQuadras.size();
    }

    public void setListaQuadras(ArrayList<Quadra> quadras){
        this.listaQuadras = quadras;
        this.listaQuadrasTodas = new ArrayList<>(quadras);
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
            List<Quadra> listaFiltrada = new ArrayList<>();
            if(constraint.toString().isEmpty()){
                listaFiltrada.addAll(listaQuadrasTodas);
            } else{
                for(Quadra quadra : listaQuadrasTodas){
                    if(quadra.getNomeQuadra().toLowerCase().contains(constraint.toString().toLowerCase())){
                        listaFiltrada.add(quadra);
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
            listaQuadras.clear();
            listaQuadras.addAll((Collection<? extends Quadra>) results.values);
            notifyDataSetChanged();
        }
    };

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
