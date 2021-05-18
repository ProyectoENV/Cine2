package com.example.proyecto_cine.Cines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyecto_cine.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityEntrada extends AppCompatActivity {

    //bbdd
    private DatabaseReference mDataBase;

    //Variables para recibir intent
    private String id_pelicula_entrada;
    private int id_cine_entrada;
    private int id_sala_entrada;
    private String hora_entrada;
    private ArrayList<String> id_asientos_reservados;
    private String Lista_ocupados_mas_reservas;
    private  String id_tabla;

    //Layout
    private TextView cine ;
    private TextView pelicula ;
    private TextView sala ;
    private TextView hora ;
    private TextView fila ;
    private TextView columna ;
    private Button pagar ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);

        mDataBase = FirebaseDatabase.getInstance().getReference();

        cine = findViewById(R.id.NombreCine);
        pelicula = findViewById(R.id.Nombrepel);
        sala = findViewById(R.id.Sala);
        hora = findViewById(R.id.Hora);
        fila = findViewById(R.id.Fila);
        columna = findViewById(R.id.Columna);
        pagar = findViewById(R.id.entradas_seleccionadas_boton);


        //insercion de los asientos ocupados
        /*Map<String, Object> map = new HashMap<>();
        map.put("/asientos_ocupados/", id_reserva);
        mDataBase.child("Peli_cine_entrada_hora").child(id_tabla).updateChildren(map);*/
        Intent recibir_datos = getIntent();
        Bundle traer_datos = recibir_datos.getExtras();
        if(recibir_datos.hasExtra("id_cine_e")){
            id_cine_entrada= Integer.parseInt(traer_datos.get("id_cine_e").toString());
            id_pelicula_entrada = traer_datos.getString("id_pelicula");
            id_sala_entrada = Integer.parseInt(traer_datos.get("id_sala_e").toString());
            hora_entrada = traer_datos.getString("hora_e");
            id_asientos_reservados = traer_datos.getStringArrayList("asientos_reservados_string_e");
            Lista_ocupados_mas_reservas = traer_datos.getString("asientos_ocupados_mas_reservas");
        }
        String filas="";
        String Columnas = "";
        for (String entrada:id_asientos_reservados) {
            String [] fila_columna =entrada.split("_");
            filas = filas+(Integer.parseInt(fila_columna[0])+1)+", ";
            Columnas = Columnas+(Integer.parseInt(fila_columna[1])+1)+", ";

        }

        buscar_cine(id_cine_entrada);
        buscar_pelicula(id_pelicula_entrada);

        sala.setText(id_sala_entrada+"");
        hora.setText(hora_entrada);
        fila.setText(filas);
        columna.setText(Columnas);
        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("/asientos_ocupados/", Lista_ocupados_mas_reservas);
                mDataBase.child("Peli_cine_entrada_hora").child(id_tabla).updateChildren(map);
                //intent activity pagar

                //guardar factura bbdd;

            }
        });

    }

    private void buscar_pelicula(String id_pelicula_entrada) {
        String[] nombre = new String[1];
        mDataBase.child("Pelicula").child(id_pelicula_entrada).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String nombre_peli = (String) snapshot.child("nombre").getValue();
                    nombre[0] = nombre_peli;
                    pelicula.setText(nombre[0]);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    private void buscar_cine(int id_cine_entrada) {
        String[] nombre = new String[1];
        mDataBase.child("Peli_cine_sala_hora").orderByChild("id_cine").equalTo(id_cine_entrada).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String nombre_cine = (String) ds.child("nombre_cine").getValue();
                   nombre[0] = nombre_cine;
                    cine.setText(nombre[0]);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}