package com.example.proyectocine.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectocine.Adapters.Adapter_facturas;
import com.example.proyectocine.MainActivity;
import com.example.proyectocine.Objetos.Factura;
import com.example.proyectocine.Objetos.Pelicula;
import com.example.proyectocine.Objetos.Usuario;
import com.example.proyectocine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    //UI
    private TextView Nombre_Usuario;
    private TextView Email;
    private EditText Cambiar_mail;
    private  EditText Cambiar_username;
    private Button Boton_Editar;
    private Button Boton_Guardar;

    //Database
    private DatabaseReference mDataBase;
    private FirebaseAuth mAuth;

    //Datos Usuario
    private String Nombre;
    private String mail;
    private String Id;
    private Usuario user;

    //modificacion usuario
    private Usuario usermodificado;

    //Facturas
    private ArrayList<Factura>facturas;
    private Adapter_facturas fAdacpter;
    private String id_pelicula;
    private String id_sala;
    private String id_cine;
    private String entradas;
    private String hora;
    private String dia;
    private String fecha_compra;
    //facturas UI
    private CardView cardview;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        mDataBase= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        facturas = new ArrayList<>();
        cardview = (CardView) root.findViewById(R.id.cardView_facturas);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerviewfacturas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        readuser();

        Nombre_Usuario = (TextView) root.findViewById(R.id.Username_Texto);
        Email = (TextView) root.findViewById(R.id.Email_texto);
        Cambiar_mail = (EditText) root.findViewById(R.id.Email_Cambiar);
        Cambiar_username =(EditText) root.findViewById(R.id.Username_Cambiar);
        Boton_Editar =(Button) root.findViewById(R.id.Boton_Editar);
        Boton_Guardar =(Button) root.findViewById(R.id.Boton_Guardar);



        Boton_Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nombre_Usuario.setVisibility(View.INVISIBLE);
                Email.setVisibility(View.INVISIBLE);
                Cambiar_mail.setVisibility(View.VISIBLE);
                Cambiar_username.setVisibility(View.VISIBLE);
                Boton_Editar.setVisibility(View.INVISIBLE);
                Boton_Guardar.setVisibility(View.VISIBLE);

                Boton_Guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        usermodificado = new Usuario();
                        if(Cambiar_username.getText().toString().equals("")||Cambiar_username.getText().toString().equals(null)){
                            usermodificado.setNombre_usuario(Nombre_Usuario.getText().toString());
                        }else{
                            usermodificado.setNombre_usuario(Cambiar_username.getText().toString());
                        }
                        if(Cambiar_mail.getText().toString().equals("")||Cambiar_mail.getText().toString().equals(null)){
                            usermodificado.setEmail(Email.getText().toString());
                        }else{
                        usermodificado.setEmail(Cambiar_mail.getText().toString());
                        }
                        Map<String, Object> map = new HashMap<>();
                        map.put("/name/", usermodificado.getNombre_usuario());
                        map.put("/email/", usermodificado.getEmail());
                        mDataBase.child("Users").child(mAuth.getUid()).updateChildren(map);
                        Intent volver_al_main = new Intent(getActivity(), MainActivity.class);
                        startActivity(volver_al_main);

                    }
                });
            }
        });
        readfacturas();

        return root;

        //sdf
    }

    private void readfacturas() {
        mDataBase.child("Factura").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            facturas.clear();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                if(Id.equals(mAuth.getUid())){
                                 id_pelicula=ds.child("id_pelicula").toString();
                                 id_sala=ds.child("id_sala").toString();
                                 id_cine=ds.child("id_cine").toString();
                                 entradas=ds.child("numero_entradas").toString();
                                 hora=ds.child("hora").toString();
                                 dia=ds.child("dia").toString();
                                 fecha_compra=ds.child("fecha_compra").toString();
                                Factura factura = new Factura(Nombre,id_pelicula,id_cine,id_sala,entradas,hora,dia,fecha_compra);
                                facturas.add(factura);
                                }
                            }
                        }

                       fAdacpter = new Adapter_facturas(facturas, R.id.recyclerviewfacturas, new Adapter_facturas.OnItemClickListener() {
                           @Override
                           public void onItemClick(Factura city, int position) {
                               //Activiti de mostrar informacion opcional
                           }
                       });
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

            }


    private void readuser() {
        mDataBase.child("Users").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    Nombre = (String) dataSnapshot.child("name").getValue();
                    mail = (String) dataSnapshot.child("email").getValue();
                    Nombre_Usuario.setText(Nombre);
                    Email.setText(mail);
                    Id = dataSnapshot.getKey();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}