package com.example.proyectocine.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectocine.Objetos.Sala;
import com.example.proyectocine.R;

import java.util.List;

public class Adapter_salas extends RecyclerView.Adapter<Adapter_salas.ViewHolder>{

    private Context context;
    private List<Sala> Salas;
    private int layout;
    private OnItemClickListener itemClickListener;
    private OnButtonClickListener btnClickListener;

    public Adapter_salas(List<Sala> Salas, int layout, OnItemClickListener itemListener, OnButtonClickListener btnListener) {
        this.Salas = Salas;
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
        holder.bind(Salas.get(position), itemClickListener, btnClickListener);
    }

    @Override
    public int getItemCount() {
        return Salas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView HoraPeli;
        public TextView SalaPeli;
        public Button siguienteEntrada;


        public ViewHolder(View itemView) {
            super(itemView);
            HoraPeli = (TextView) itemView.findViewById(R.id.hora);
            SalaPeli = (TextView) itemView.findViewById(R.id.sala);
            siguienteEntrada = (Button) itemView.findViewById(R.id.BotonSalasSiguiente);

        }

        public void bind(final Sala sala, final OnItemClickListener itemListener, final OnButtonClickListener btnListener) {
            String hora_peli = sala.getHora();
            String sala_peli = sala.getNumerosala();
            HoraPeli.setText(hora_peli);
            SalaPeli.setText(sala_peli);

            siguienteEntrada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnListener.onButtonClick(sala, getAdapterPosition());
                }
            });


            itemView.setOnClickListener(view -> itemListener.onItemClick(sala, getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Sala room, int position);
    }

    public interface OnButtonClickListener {
        void onButtonClick(Sala salaa, int position);
    }


}
