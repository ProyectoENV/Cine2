package com.example.proyecto_cine.entradas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.proyecto_cine.Objetos.Cine;
import com.example.proyecto_cine.Objetos.Sala;
import com.example.proyecto_cine.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class botonera_entradas_activity extends AppCompatActivity {
    private DatabaseReference mDataBase;

    private ImageButton[][] botones;
    private Button siguiente;
     // getintent
    private String id_pelicula_sala;
    private int id_cine_sala;
    private int id_sala_sala;
    private String hora;
    //id tabla
    String id_tabla;

    private ArrayList<Integer> id_asientos_ocupados;

    private ArrayList<Integer> id_asientos_reservados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botonera_entradas_activity);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        id_asientos_ocupados= new ArrayList<>();
        id_asientos_reservados= new ArrayList<>();
        botones = new ImageButton[4][10];
        siguiente = (Button) findViewById(R.id.entradas_seleccionadas_boton);
        Intent recogida = getIntent();
        Bundle recogida_bundle=recogida.getExtras();
        if(recogida.hasExtra("id_cine_s")) {
             //recogida_bundle =recogida.getExtras();
            id_cine_sala= Integer.parseInt(recogida_bundle.get("id_cine_s").toString());
            id_pelicula_sala = recogida_bundle.getString("id_pelicula");
            //id_cine_sala = Integer.parseInt( recogida_bundle.getString("id_sala_s"));
            id_tabla= recogida_bundle.getString("id_tabla");
            id_sala_sala = Integer.parseInt(recogida_bundle.get("id_sala_s").toString());
            hora = recogida_bundle.getString("hora");

        }
        Log.d("prueba main", id_asientos_ocupados.toString());
        buscarasientos(id_pelicula_sala,id_cine_sala,id_sala_sala,hora);


        //buscarasientos(id_pelicula_sala,id_cine_sala,id_sala_sala,hora);
        Log.d("prueba main", id_asientos_ocupados.toString());




    }

    private void metodo_interfaz() {
        Log.d("prueba metodo", id_asientos_ocupados.toString());
        for( int i =0;i<4;i++){
            for( int j =0;j<10;j++){
                String boton = "boton"+i+"_"+j;
                int id=getResources().getIdentifier(boton,"id",getPackageName());
                int f=i,h=j;
                botones[i][j] = (ImageButton) findViewById(id);
                if(id_asientos_ocupados.contains(id)){
                    int id_imagen =R.drawable.butaca_ocupada;
                    botones[i][j].setImageResource(id_imagen);
                    botones[i][j].setEnabled(false);
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
                    String id_reserva = "";
                    for (int id:id_asientos_ocupados) {
                        id_reserva=id_reserva+id+",";
                    }
                    for (int id:id_asientos_reservados) {
                        id_reserva=id_reserva+id+",";
                    }
                    Map<String, Object> map = new HashMap<>();
                    map.put("/asientos_ocupados/", id_reserva);
                    mDataBase.child("Peli_cine_sala_hora").child(id_tabla).updateChildren(map);
                }
            }
        });
    }

    public void buscarasientos(String id_pelicula_sala, int id_cine_sala, int id_sala_sala, String hora){
        mDataBase.child("Peli_cine_sala_hora").orderByChild("id_pelicula").equalTo(id_pelicula_sala).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    id_asientos_ocupados.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        int id_cine_bbdd = Integer.parseInt(ds.child("id_cine").getValue().toString());
                        int id_sala_bbdd = Integer.parseInt(ds.child("id_sala").getValue().toString());
                        String hora_bbdd = ds.child("hora").getValue().toString();
                        if(id_cine_bbdd==id_cine_sala&&id_sala_bbdd==id_sala_sala&&hora.equals(hora_bbdd)){
                            String listaids= ds.child("asientos_ocupados").getValue().toString();
                            String[]arrayids=listaids.split(",");
                            for (String a:arrayids) {
                                if(!a.equals("")){
                                int b = Integer.parseInt(a);
                                id_asientos_ocupados.add(b);
                                Log.d("prueba metodo busqueda1", id_asientos_ocupados.toString());}
                            }
                            Log.d("prueba metodo busqueda2", id_asientos_ocupados.toString());
                        }
                        Log.d("prueba metodo busqueda3", id_asientos_ocupados.toString());
                    }
                    metodo_interfaz();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
}

}
