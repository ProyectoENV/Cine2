package com.example.proyectocine.Cines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.proyectocine.R;
import com.example.proyectocine.ui.MainActivity;

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
    protected  void onDestroy() {

        super.onDestroy();
        startActivity(new Intent(Sincines.this, MainActivity.class));
    }
}