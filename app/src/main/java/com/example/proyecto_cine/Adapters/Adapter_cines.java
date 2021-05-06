package com.example.proyecto_cine.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_cine.Objetos.Cine;
import com.example.proyecto_cine.Objetos.Pelicula;
import com.example.proyecto_cine.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_cines extends RecyclerView.Adapter<Adapter_cines.ViewHolder>{
    private Context context;
    private List<Cine> Cines;
    private int layout;
    private OnItemClickListener itemClickListener;
    private OnButtonClickListener btnClickListener;

    public Adapter_cines(List<Cine> Cines, int layout, OnItemClickListener itemListener, OnButtonClickListener btnListener) {
        this.Cines = Cines;
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
        holder.bind(Cines.get(position), itemClickListener, btnClickListener);
    }

    @Override
    public int getItemCount() {
        return Cines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Nombrecine;
        public TextView Ubicacion;
        public Button siguienteSala;


        public ViewHolder(View itemView) {
            super(itemView);
            Nombrecine = (TextView) itemView.findViewById(R.id.Nombrecine);
            Ubicacion = (TextView) itemView.findViewById(R.id.ubicacion);
            siguienteSala = (Button) itemView.findViewById(R.id.BotonCinesSiguiente);

        }

        public void bind(final Cine cine, final OnItemClickListener itemListener, final OnButtonClickListener btnListener) {
            Nombrecine.setText(cine.getNombre());
            Ubicacion.setText(cine.getLocalizacion());

            siguienteSala.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnListener.onButtonClick(cine, getAdapterPosition());
                }
            });


            itemView.setOnClickListener(view -> itemListener.onItemClick(cine, getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Cine city, int position);
    }

    public interface OnButtonClickListener {
        void onButtonClick(Cine film, int position);
    }
}
