package com.example.proyectocine.Cines;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.proyectocine.R;

public class Sincines extends AppCompatActivity {
    private ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincines);
        imagen = (ImageView) findViewById(R.id.PeligroIV);
        int idImagen =R.drawable.peligro;
        imagen.setImageResource(idImagen);

    }
}