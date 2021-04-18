package com.example.proyecto_cine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_cine.Api.Api;
import com.example.proyecto_cine.Api.ApiServices.FilmService;
import com.example.proyecto_cine.Objetos.Pelicula;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoPelicula extends AppCompatActivity {
    private String titulo ;

    DatabaseReference mDataBase;
    //ui
    private TextView Titulo;
    private TextView Director;
    private TextView Sinopsis;
    private TextView Genero;
    private ImageView Poster;
    private TextView Año;
    private Button Reserva21;

    private FilmService service;
    private Call<Pelicula> FilmCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pelicula);
        Intent intentRecibir  = getIntent();
        if(intentRecibir.hasExtra("Titulo")) {
            Bundle traertitulo = intentRecibir.getExtras();
            titulo = traertitulo.getString("Titulo");
        }else{
            Toast.makeText(InfoPelicula.this, "Fallo ",Toast.LENGTH_LONG);
        }
        //titulo="="+Titulo;
        setui();
        service = Api.getApi().create(FilmService.class);
        if (titulo != "") {
            FilmCall = service.getFilm(titulo, Api.APPKEY);
            FilmCall.enqueue(new Callback<Pelicula>() {
                @Override
                public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                    Pelicula peli = response.body();
                    if(peli == null){
                        Toast.makeText(InfoPelicula.this, "Fallo ",Toast.LENGTH_LONG);
                    }else{
                    setResult(peli);}
                }

                @Override
                public void onFailure(Call<Pelicula> call, Throwable t) {
                    Toast.makeText(InfoPelicula.this, "Error", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(this,"Fallo",Toast.LENGTH_LONG);
        }

    }
    private void setui(){
        Titulo =(TextView) findViewById(R.id.Titulo);
        Director = (TextView) findViewById((R.id.Director));
        Sinopsis =(TextView) findViewById(R.id.Sinopsis);
        Genero = (TextView) findViewById((R.id.Genero));
        Año = (TextView) findViewById((R.id.Lanzamiento));
        Poster =(ImageView) findViewById((R.id.Imagen_Pelicula));
        Reserva21 = (Button) findViewById(R.id.BotonReserva);
        Reserva21.setVisibility(View.INVISIBLE);


    }
    private void setResult(Pelicula Peli) {
        Titulo.setText("Titulo: "+Peli.getNombre());
        Director.setText("Director: "+Peli.getDirector());
        Sinopsis.setText("Sinopsis: "+Peli.getSinopsis());
        Genero.setText("Genero: "+Peli.getGenero());
        Año.setText("Año de lanzamiento: "+Peli.getAño());
        Picasso.get().load(Peli.getImagen()).fit().into(Poster);
        if(Peli.getAño()==2021){
            Reserva21.setVisibility(View.VISIBLE);
            /*Map<String, Object> map = new HashMap<>();
            map.put("Pelicula",Peli);

            mDataBase.child("Pelicula").setValue(map).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(InfoPelicula.this, "El Pelicula se ha creado corectamente", Toast.LENGTH_SHORT).show();
                }else{

                }
            });*/
        }
    }

}