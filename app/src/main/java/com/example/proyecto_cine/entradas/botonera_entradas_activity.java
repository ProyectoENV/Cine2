package com.example.proyecto_cine.entradas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.proyecto_cine.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class botonera_entradas_activity extends AppCompatActivity {
    private DatabaseReference mDataBase;

    private ImageButton[][] botones;
    private Button siguiente;

    private String id_pelicula_sala;
    private String id_cine_sala;
    private String id_sala_sala;
    private String hora;

    private ArrayList<Integer> id_asientos_ocupados;

    private ArrayList<Integer> id_asientos_reservados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botonera_entradas_activity);

        id_asientos_ocupados= new ArrayList<>();
        id_asientos_reservados= new ArrayList<>();
        botones = new ImageButton[4][10];
        siguiente = (Button) findViewById(R.id.entradas_seleccionadas_boton);

        for( int i =0;i<4;i++){
            for( int j =0;j<10;j++){
                String boton = "boton"+i+"_"+j;
                int id=getResources().getIdentifier(boton,"id",getPackageName());
                int f=i,h=j;
                botones[i][j] = (ImageButton) findViewById(id);
                for (int a:id_asientos_ocupados) {
                    if(id==a){
                        int id_imagen =R.drawable.butaca_ocupada;
                        botones[i][j].setImageResource(id_imagen);
                    }
                }
                botones[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(id_asientos_reservados.contains(id) ){
                            int index_borrar=id_asientos_reservados.indexOf(id);
                            id_asientos_reservados.remove(index_borrar);
                            int id_imagen =R.drawable.butaca_libre;
                            botones[f][h].setImageResource(id_imagen);
                        }else{
                        id_asientos_reservados.add(id);
                        int id_imagen =R.drawable.butaca_reservada;
                        botones[f][h].setImageResource(id_imagen);
                        }
                    }
                });
            }
        }
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_asientos_reservados.isEmpty()){
                    Toast.makeText(botonera_entradas_activity.this, "Seleccione al menos un asiento, el precio por entrada son 8,35€",Toast.LENGTH_SHORT).show();
                }else{
                    double precio = id_asientos_reservados.size()*8.35;
                    Toast.makeText(botonera_entradas_activity.this, "Ha seleccionado "+id_asientos_reservados.size()+" entradas, con un precio de "+precio+"€",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //buscarasientos(id_pelicula_sala,id_cine_sala,id_sala_sala,hora);




    }
    public void buscarasientos(String id_pelicula_sala, String id_cine_sala, String id_sala_sala, String hora){
        mDataBase.child("Peli_cine_sala_hora").orderByChild("id_pelicula").equalTo(id_pelicula_sala).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
}
}
