package com.example.proyectocine.ui.home;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectocine.Adapters.Adapter_pelicula;
import com.example.proyectocine.Cines.ActivityCines;
import com.example.proyectocine.LoginRegister.ActivityLogin;
import com.example.proyectocine.MainActivity;
import com.example.proyectocine.Objetos.Pelicula;
import com.example.proyectocine.R;
import com.example.proyectocine.entradas.botonera_entradas_activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    //Peliculas
    private List<Pelicula> Lista_peliculas;
    private Adapter_pelicula P_Adapter;
    private String id;
    private String Nombre;
    private String Director;
    private String Sinopsis;
    private String Imagen ;
    private int Año;
    private java.lang.String Genero;
    //Recyclerview
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CardView cardview;
    //database
    private DatabaseReference mDataBase;
    // Botones
    private EditText ET_Buscador;
    private String Pelicula_buscar;
    private Button BT_Buscador;

    private HomeViewModel homeViewModel;
    private boolean isConnected;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //instacia bbdd
        mDataBase = FirebaseDatabase.getInstance().getReference();

        //Recyclerview
        Lista_peliculas = new ArrayList<Pelicula>();
        cardview = (CardView) root.findViewById(R.id.cardView_peliculas);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView_pelicula);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //Layout buscador
        ET_Buscador =  root.findViewById(R.id.BuscadorText);
        BT_Buscador = (Button) root.findViewById(R.id.BotonBuscar);
        BT_Buscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm =
                        (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(isConnected == true ){
                Pelicula_buscar = ET_Buscador.getText().toString();
                if(Pelicula_buscar=="" || Pelicula_buscar.equals("Name")){
                    Toast.makeText(getContext(),"Introduzca el titulo de una pelicula", Toast.LENGTH_LONG);
                }else{
                    boolean insertardatos = true;
                Intent intentinfo = new Intent(getActivity(), InfoPelicula.class);
                    String Buscar_igual ;
                    for (Pelicula film: Lista_peliculas) {
                        Buscar_igual =Pelicula_buscar;
                        if(film.getNombre().equals(Buscar_igual)){
                            insertardatos=false;
                        }
                    }
                intentinfo.putExtra("Titulo",Pelicula_buscar);
                intentinfo.putExtra("insertardatos", insertardatos);
                startActivity(intentinfo);}
                }else{
                    Toast.makeText(getContext(),"Conecte su dispositivo a internet antes continuar ", Toast.LENGTH_LONG).show();
                }
            }
        });

        readPelicula();


        return root;
    }
    private void readPelicula() {
        mDataBase.child("Pelicula").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    Lista_peliculas.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Nombre = ds.child("nombre").getValue().toString();
                        Director = ds.child("director").getValue().toString();
                        Sinopsis = ds.child("sinopsis").getValue().toString();
                        Genero =  ds.child("genero").getValue().toString();
                        Año =  Integer.parseInt(ds.child("año").getValue().toString());
                        Imagen =  ds.child("imagen").getValue().toString();
                        id = ds.getKey();
                        Pelicula pelicula = new Pelicula( id,Nombre, Director, Sinopsis, Genero, Imagen,Año);
                        Lista_peliculas.add(pelicula);

                    }
                }

                 P_Adapter= new Adapter_pelicula(Lista_peliculas, R.layout.recyclerviewitem, new Adapter_pelicula.OnItemClickListener(){
                     @Override
                     public void onItemClick(Pelicula film, int position) {
                         ConnectivityManager cm =
                                 (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);

                         NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                         isConnected = activeNetwork != null &&
                                 activeNetwork.isConnectedOrConnecting();
                         if(isConnected == false ){
                             Toast.makeText(getActivity(),"Conecte su dispositivo a internet antes de hacer el continuar", Toast.LENGTH_LONG).show();
                             startActivity(new Intent(getActivity(), MainActivity.class));
                         }else{
                         boolean insertardatos= false;
                        Intent mostarinfo = new Intent(getActivity(), InfoPelicula.class);
                        mostarinfo.putExtra("Titulo",film.getNombre() );
                        mostarinfo.putExtra("insertardatos",insertardatos);
                        startActivity(mostarinfo);
                         }
                     }

                }, new Adapter_pelicula.OnButtonClickListener() {
                     @Override
                     public void onButtonClick(Pelicula film, int position) {
                         Intent iniciar_activitiecine = new Intent(getActivity(), ActivityCines.class);
                         iniciar_activitiecine.putExtra("id_pelicula",film.getId() );
                         startActivity(iniciar_activitiecine);
                     }

                });
                recyclerView.setAdapter(P_Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }
}