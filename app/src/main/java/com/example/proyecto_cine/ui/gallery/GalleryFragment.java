package com.example.proyecto_cine.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyecto_cine.ActivityLogin;
import com.example.proyecto_cine.MainActivity;
import com.example.proyecto_cine.Objetos.Pelicula;
import com.example.proyecto_cine.Objetos.Usuario;
import com.example.proyecto_cine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        mDataBase= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


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


        return root;

        //sdf
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
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}