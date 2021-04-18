package com.example.proyecto_cine.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_cine.Objetos.Pelicula;
import com.example.proyecto_cine.R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_pelicula extends RecyclerView.Adapter<Adapter_pelicula.ViewHolder> {
    private Context context;
    private List<Pelicula> Peliculas;
    private int layout;
    private OnItemClickListener itemClickListener;
    private OnButtonClickListener btnClickListener;


    public Adapter_pelicula(List<Pelicula> Peliculas, int layout, OnItemClickListener itemListener, OnButtonClickListener btnListener) {
        this.Peliculas = Peliculas;
        this.layout = layout;
        this.itemClickListener = itemListener;
        this.btnClickListener = btnListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(Peliculas.get(position), itemClickListener, btnClickListener);
    }

    @Override
    public int getItemCount() {
        return Peliculas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Nombrepelicula;
        public TextView Genero;
        public TextView Sinopsis;
        public ImageView Poster;
        public Button reservar;


        public ViewHolder(View itemView) {
            super(itemView);
            Nombrepelicula = (TextView) itemView.findViewById(R.id.Nombrepelicula);
            Genero = (TextView) itemView.findViewById(R.id.Genero);
            Sinopsis = (TextView) itemView.findViewById(R.id.Sinopsis);
            Poster = (ImageView) itemView.findViewById(R.id.Posterpelicula);
            reservar = (Button) itemView.findViewById(R.id.BotonCines);

        }

        public void bind(final Pelicula Pelicula, final OnItemClickListener itemListener, final OnButtonClickListener btnListener) {
            Nombrepelicula.setText(Pelicula.getNombre());
            Genero.setText(Pelicula.getGenero());
            Sinopsis.setText(Pelicula.getSinopsis());
            Picasso.get().load(Pelicula.getImagen()).fit().into(Poster);
            reservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnListener.onButtonClick(Pelicula, getAdapterPosition());
                }
            });


            itemView.setOnClickListener(view -> itemListener.onItemClick(Pelicula, getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Pelicula city, int position);
    }

    public interface OnButtonClickListener {
        void onButtonClick(Pelicula film, int position);
    }

}

