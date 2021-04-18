package com.example.proyecto_cine.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_cine.Adapters.Adapter_pelicula;
import com.example.proyecto_cine.InfoPelicula;
import com.example.proyecto_cine.MainActivity;
import com.example.proyecto_cine.Objetos.Genero;
import com.example.proyecto_cine.Objetos.Pelicula;
import com.example.proyecto_cine.R;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);

        mDataBase = FirebaseDatabase.getInstance().getReference();

        //Lista_peliculas = new ArrayList<Pelicula>();
        //cardview = (CardView) root.findViewById(R.id.cardView);
        //layoutManager = new LinearLayoutManager(getContext());
        //recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        ET_Buscador =  root.findViewById(R.id.BuscadorText);
        BT_Buscador = (Button) root.findViewById(R.id.BotonBuscar);
        BT_Buscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pelicula_buscar = ET_Buscador.getText().toString();
                if(Pelicula_buscar=="" || Pelicula_buscar.equals("Name")){
                    Toast.makeText(getContext(),"Introduzca el titulo de una pelicula", Toast.LENGTH_LONG);
                }else{
                Intent intentinfo = new Intent(getActivity(), InfoPelicula.class);
                intentinfo.putExtra("Titulo",Pelicula_buscar);
                startActivity(intentinfo);}
            }
        });

        //readPelicula();


        return root;
    }
    /*private void readPelicula() {
        mDataBase.child("Peliculas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    Lista_peliculas.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Nombre = ds.child("name").getValue().toString();
                        Director = ds.child("Director").getValue().toString();
                        Sinopsis = ds.child("Sinopsis").getValue().toString();
                        Genero =  ds.child("genero").getValue().toString();
                        Imagen =  ds.child("imagen").getValue().toString();
                        id = ds.getKey();

                        Pelicula pelicula = new Pelicula(id, Nombre, Director, Sinopsis, Genero, Imagen);
                        Lista_peliculas.add(pelicula);

                    }
                }

                 P_Adapter= new Adapter_pelicula(Lista_peliculas, R.layout.recyclerview_item_pelicula, new Adapter_pelicula.OnItemClickListener(){
                     @Override
                     public void onItemClick(Pelicula city, int position) {

                     }

                }, new Adapter_pelicula.OnButtonClickListener() {
                     @Override
                     public void onButtonClick(Pelicula film, int position) {

                     }

                });
                recyclerView.setAdapter(P_Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }*/
}