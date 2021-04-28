package com.example.proyecto_cine.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    //UI
    private TextView Nombre_Usuario;
    private TextView Email;
    private TextView Imagen_Texto;
    private ImageView Foto_perfil;
    private EditText Cambiar_mail;
    private  EditText Cambiar_username;
    private EditText Cambiar_Foto;
    private Button Boton_Editar;
    private Button Boton_Guardar;
    //Database
    private DatabaseReference mDataBase;
    private FirebaseAuth mAuth;
    //Datos Usuario
    private String Nombre;
    private String mail;
    private String Imagen;
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

        Nombre_Usuario = (TextView) root.findViewById(R.id.TV_NombrePerfil);
        Email = (TextView) root.findViewById(R.id.Email_texto);
        Foto_perfil = (ImageView) root.findViewById(R.id.ImagenPerfil);
        Cambiar_mail = (EditText) root.findViewById(R.id.Email_Cambiar);
        Cambiar_username =(EditText) root.findViewById(R.id.Username_Cambiar);
        Cambiar_Foto =(EditText) root.findViewById(R.id.Imagen_Cambiar);
        Boton_Editar =(Button) root.findViewById(R.id.Boton_Editar);
        Boton_Guardar =(Button) root.findViewById(R.id.Boton_Guardar);

        Nombre_Usuario.setText(user.getNombre_usuario());
        Email.setText(user.getEmail());
        Picasso.get().load(user.getImagen_usuario()).fit().into(Foto_perfil);

        Boton_Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nombre_Usuario.setVisibility(View.INVISIBLE);
                Email.setVisibility(View.INVISIBLE);
                Cambiar_Foto.setVisibility(View.VISIBLE);
                Cambiar_mail.setVisibility(View.VISIBLE);
                Cambiar_username.setVisibility(View.VISIBLE);
                Imagen_Texto.setVisibility(View.VISIBLE);
                Boton_Guardar.setVisibility(View.VISIBLE);

                Boton_Guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        usermodificado = new Usuario();
                        usermodificado.setEmail(Cambiar_mail.getText().toString());
                        usermodificado.setNombre_usuario(Cambiar_username.getText().toString());
                        usermodificado.setImagen_usuario(Cambiar_Foto.getText().toString());
                        mDataBase.push().setValue(usermodificado);

                    }
                });
            }
        });


        return root;

        //sdf
    }
    private void readuser() {
        mDataBase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    user = new Usuario();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(mAuth.getCurrentUser().getUid().equals(ds.getKey())){
                        Nombre = ds.child("name").getValue().toString();
                        mail = ds.child("email").getValue().toString();
                        //Imagen =  ds.child("imagen").getValue().toString();
                        Id = ds.getKey();
                        user = new Usuario( Id,Nombre, mail, Imagen);
                        }


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}