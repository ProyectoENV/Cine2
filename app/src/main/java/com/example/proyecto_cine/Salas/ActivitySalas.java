package com.example.proyecto_cine.Salas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;


import com.example.proyecto_cine.Adapters.Adapter_salas;
import com.example.proyecto_cine.MainActivity;
import com.example.proyecto_cine.Objetos.Sala;
import com.example.proyecto_cine.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivitySalas extends AppCompatActivity {

    //Salas
    private List<Sala> Lista_salas;
    private Adapter_salas S_Adapter;
    private String id;
    private String Hora;
    private String SalaPel;
    private String id_Cine;
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
        cardview = (CardView) findViewById(R.id.cardView_salas);
        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_salas);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        Intent recibir_id  = getIntent();
        if(recibir_id.hasExtra("id_cine")) {
            Bundle id_cine = recibir_id.getExtras();
            id_Cine = id_cine.getString("id_cine");
        }

        readSalas(id_Cine);
    }

    private void readSalas(String id_cine_buscar) {
        mDataBase.child("Peli_cine_sala_hora").orderByChild("id_cine").equalTo(id_cine_buscar).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    Lista_salas.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Hora = ds.child("hora").getValue().toString();
                        SalaPel = ds.child("id_sala").getValue().toString();
                        id = ds.getKey();
                        Sala sala = new Sala(id,Hora, SalaPel);
                        Lista_salas.add(sala);

                    }
                }

                S_Adapter= new Adapter_salas(Lista_salas, R.layout.recyclerviewitemsalas, new Adapter_salas.OnItemClickListener(){
                    @Override
                    public void onItemClick(Sala sala, int position) {

                    }

                }, new Adapter_salas.OnButtonClickListener() {
                    @Override
                    public void onButtonClick(Sala room, int position) {

                        // intent que pasa al acitivity de la entrada
                        startActivity(new Intent(ActivitySalas.this, MainActivity.class)); //Cmabiar a la clase de salas
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