package com.example.proyectocine.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectocine.Objetos.Factura;
import com.example.proyectocine.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_facturas extends RecyclerView.Adapter<Adapter_facturas.ViewHolder> {
    private Context context;
    private List<Factura> facturas;
    private int layout;
    private OnItemClickListener itemClickListener;


    public Adapter_facturas(List<Factura> facturas, int layout, OnItemClickListener itemListener) {
        this.facturas = facturas;
        this.layout = layout;
        this.itemClickListener = itemListener;
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
        holder.bind(facturas.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return facturas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Cine;
        public TextView Sala;
        public TextView pelicula;
        public TextView dia;
        public TextView hora;
        public TextView fecha_compra;
        public TextView Precio;


        public ViewHolder(View itemView) {
            super(itemView);
            Cine = (TextView) itemView.findViewById(R.id.cineFacturas);
            Sala = (TextView) itemView.findViewById(R.id.salaFactura);
            pelicula = (TextView) itemView.findViewById(R.id.peliculaFactura);
            dia = (TextView) itemView.findViewById(R.id.Dia);
            hora = (TextView) itemView.findViewById(R.id.horaFactura);
            fecha_compra = (TextView) itemView.findViewById(R.id.fechaCompra);
            Precio = (TextView) itemView.findViewById(R.id.Precio);

        }

        public void bind(final Factura Factura, final OnItemClickListener itemListener ) {
            Cine.setText("Cine: "+Factura.getId_cine());
            pelicula.setText("Pelicula: "+Factura.getId_pelicula());
            Sala.setText("Sala: "+Factura.getId_sala());
            dia.setText("Dia: "+Factura.getDia());
            hora.setText("Hora: "+Factura.getHora());
            Precio.setText("Precio: "+(Integer.parseInt(Factura.getNumero_entradas())+8.35)+"€");
            fecha_compra.setText("Fecha de adquisicion: "+Factura.getFecha_compra());
            itemView.setOnClickListener(view -> itemListener.onItemClick(Factura, getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Factura city, int position);
    }



}

