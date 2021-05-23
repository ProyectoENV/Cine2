package com.example.proyectocine.Salas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.example.proyectocine.Adapters.Adapter_salas;
import com.example.proyectocine.Objetos.Sala;
import com.example.proyectocine.R;
import com.example.proyectocine.entradas.botonera_entradas_activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ActivitySalas extends AppCompatActivity {

    //Salas
    private List<Sala> Lista_salas;
    private Adapter_salas S_Adapter;
    private String id;
    private String Hora;
    private String SalaPel;
    private String id_pelicula;
    private int  id_Cine;
    //Recyclerview
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CardView cardview;
    //database
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salas);

        //instacia bbdd
        mDataBase = FirebaseDatabase.getInstance().getReference();

        //Recyclerview
        Lista_salas = new ArrayList<Sala>();
        cardview = (CardView) findViewById(R.id.cardView_facturas);
        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_salas);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        Intent recibir_id  = getIntent();
        if(recibir_id.hasExtra("id_cine")) {
            Bundle id_cine = recibir_id.getExtras();
            id_Cine = Integer.parseInt(id_cine.getString("id_cine"));
            id_pelicula = id_cine.getString("id_pelicula");
        }
        readSalas(id_Cine, id_pelicula);

    }

    private void readSalas(int id_cine_buscar, String id_pelicula) {
        mDataBase.child("Peli_cine_sala_hora").orderByChild("id_pelicula").equalTo(id_pelicula).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Lista_salas.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            int id_cine_bbdd =Integer.parseInt(ds.child("id_cine").getValue().toString());
                            if (id_cine_buscar==id_cine_bbdd) {
                            Hora = ds.child("hora").getValue().toString();
                            SalaPel = ds.child("id_sala").getValue().toString();
                            String Dia = ds.child("dia").getValue().toString();
                            id = ds.getKey();
                            Sala sala = new Sala(id, Hora, SalaPel, Dia);
                            Lista_salas.add(sala);

                        }
                    }
                }

                //Comprobacion hora para poner dia acorde la hora
                String horaActual = new SimpleDateFormat("dd:HH:mm", Locale.getDefault()).format(new Date());
                horaActual = horaActual.replace(":","");
                int hora_actual_int = Integer.parseInt(horaActual);
                String diaActual = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
                int dia_actual_int=Integer.parseInt(diaActual);

                for (Sala sala:Lista_salas) {
                    String diahora = sala.getNumerosala()+dia_actual_int;
                    if(hora_actual_int>Integer.parseInt(diahora)){
                        dia_actual_int++;
                    }
                }
                Log.d("hora",dia_actual_int+"");
                S_Adapter= new Adapter_salas(Lista_salas, R.layout.recyclerviewitemsalas, new Adapter_salas.OnItemClickListener(){
                    @Override
                    public void onItemClick(Sala sala, int position) {

                    }

                }, new Adapter_salas.OnButtonClickListener() {
                    @Override
                    public void onButtonClick(Sala room, int position) {

                        // intent que pasa al acitivity de la entrada
                        Intent datos_sin_asientos = new Intent(ActivitySalas.this, botonera_entradas_activity.class); //Cmabiar a la clase de salas
                        datos_sin_asientos.putExtra("id_cine_s", id_Cine);
                        datos_sin_asientos.putExtra("id_pelicula", id_pelicula);
                        datos_sin_asientos.putExtra("id_tabla", room.getIdSala());
                        datos_sin_asientos.putExtra("id_sala_s", room.getNumerosala());
                        datos_sin_asientos.putExtra("hora", room.getHora());
                        startActivity(datos_sin_asientos);
                    }

                });
                recyclerView.setAdapter(S_Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }
}