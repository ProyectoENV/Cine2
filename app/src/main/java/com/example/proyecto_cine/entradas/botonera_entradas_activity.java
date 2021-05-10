package com.example.proyecto_cine.entradas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.proyecto_cine.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class botonera_entradas_activity extends AppCompatActivity {
    private ImageButton[][] botones;

    private DatabaseReference mDataBase;
    private String id_pelicula_sala;
    private String id_cine_sala;
    private String id_sala_sala;
    private String hora;

    private Integer [] [] asientosocupados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botonera_entradas_activity);

        botones = new ImageButton[4][10];
        for(int i =0;i<4;i++){
            for(int j =0;j<10;j++){
                String boton = "boton"+i+"_"+j;
                int id=getResources().getIdentifier(boton,"id",getPackageName());
                botones[i][j] = (ImageButton) findViewById(id);
            }
        }
        asientosocupados = new Integer[4][10];
        buscarasientos(id_pelicula_sala,id_cine_sala,id_sala_sala,hora);




    }
    public void buscarasientos(String id_pelicula_sala, String id_cine_sala, String id_sala_sala, String hora){
        mDataBase.child("Peli_cine_sala_hora").orderByChild("id_pelicula").equalTo(id_pelicula_sala).orderByChild("id_cine").equalTo(id_cine_sala).orderByChild("id_sala").equalTo(id_sala_sala).orderByChild("hora").equalTo(hora).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
}
}
