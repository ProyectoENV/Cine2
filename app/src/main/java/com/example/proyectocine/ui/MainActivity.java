package com.example.proyectocine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.proyectocine.LoginRegister.ActivityLogin;
import com.example.proyectocine.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;
    private TextView Usuario;
    private String Email_usuario;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //fhjghjgf
        mDataBase = FirebaseDatabase.getInstance().getReference();

        //Limpieza de los asientos en funcion de la hora
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        String currentday = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        currentTime = currentTime.replace(":","");
        resetasientos(currentTime, currentday);

        Intent RecogerUsuario = getIntent();
        if(RecogerUsuario.hasExtra("Mail")){
        Bundle B_Recibir = RecogerUsuario.getExtras();
        Email_usuario = B_Recibir.getString("Mail");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.TV_EmailPerfil);
        navUsername.setText(Email_usuario);}

        //instanciamos la base de datos
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    private void resetasientos(String hora_actual, String Currentday) {
        mDataBase.child("Peli_cine_sala_hora").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String hora_sala=ds.child("hora").getValue().toString();
                        String id_tabla = ds.getKey();
                        hora_sala= hora_sala.replace(":","");
                        String dia = ds.child("dia").getValue().toString();
                        int horaintactual = Integer.parseInt(hora_actual);
                        int horaintsala = Integer.parseInt(hora_sala);
                        int diaintsal = Integer.parseInt(dia);
                        int diaintactual = Integer.parseInt(Currentday);
                        if(horaintactual>horaintsala&&diaintsal==diaintactual){
                            diaintactual++;
                            Map<String, Object> map = new HashMap<>();
                            map.put("/dia/",diaintactual);
                            map.put("/asientos_ocupados/", "");
                            mDataBase.child("Peli_cine_sala_hora").child(id_tabla).updateChildren(map);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //para cuando presionemos la opción de cerrar sesión se eliminen se vuelva a la primera pantalla y otro usuario entre
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.logOut){

            mAuth.signOut();//cerramos sesión en Firebase
            startActivity(new Intent(MainActivity.this, ActivityLogin.class)); // vuelvo a la pantalla de inicio de sesión
            finish(); //de esta forma no puede volver atras
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}