package com.example.proyecto_cine.Cines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.proyecto_cine.Adapters.Adapter_cines;
import com.example.proyecto_cine.Adapters.Adapter_pelicula;
import com.example.proyecto_cine.InfoPelicula;
import com.example.proyecto_cine.MainActivity;
import com.example.proyecto_cine.Objetos.Cine;
import com.example.proyecto_cine.Objetos.Pelicula;
import com.example.proyecto_cine.R;
import com.example.proyecto_cine.RegisterActivity;
import com.example.proyecto_cine.entradas.botonera_entradas_activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityCines extends AppCompatActivity {

    //Cines
    private List<Cine> Lista_cines;
    private Adapter_cines A_Adapter;
    private String id;
    private String Nombre;
    private String Ubicacion;
    private String id_pel;
    //Recyclerview
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CardView cardview;
    //database
    private DatabaseReference mDataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cines);

        //instacia bbdd
        mDataBase = FirebaseDatabase.getInstance().getReference();

        //Recyclerview
        Lista_cines = new ArrayList<Cine>();
        cardview = (CardView) findViewById(R.id.cardView_cines);
        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_cines);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        Intent recibir_id  = getIntent();
        if(recibir_id.hasExtra("id_pelicula")) {
            Bundle id_pelicula = recibir_id.getExtras();
            id_pel = id_pelicula.getString("id_pelicula");
        }

        readCines(id_pel);
    }

    private void readCines(String id_pelicula_buscar) {
        mDataBase.child("Peli_cine_sala_hora").orderByChild("id_pelicula").equalTo(id_pelicula_buscar).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    Lista_cines.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Nombre = ds.child("nombre_cine").getValue().toString();
                        Ubicacion = ds.child("localizacion_cine").getValue().toString();
                        id = ds.getKey();
                        Cine cine = new Cine(id,Nombre, Ubicacion);
                        Lista_cines.add(cine);

                    }
                }

                A_Adapter= new Adapter_cines(Lista_cines, R.layout.recyclerviewitemcines, new Adapter_cines.OnItemClickListener(){
                    @Override
                    public void onItemClick(Cine cine, int position) {

                    }

                }, new Adapter_cines.OnButtonClickListener() {
                    @Override
                    public void onButtonClick(Cine film, int position) {

                        // intent que pasa al acitivity de salas
                        startActivity(new Intent(ActivityCines.this, botonera_entradas_activity.class)); //Cmabiar a la clase de salas
                    }

                });
                recyclerView.setAdapter(A_Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }
}