package com.example.proyecto_cine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText mEditTextEmailRegister;
    private EditText mEditTextPasswordRegister;
    private EditText mEditTextNameRegister;
    private Button btnRegistrarRegister;

    //Variables de los datos
    private String name = "";
    private String email = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //instanciamos la base de datos y los campos
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mEditTextNameRegister = (EditText) findViewById(R.id.NameEditTextRegister);
        mEditTextEmailRegister = (EditText) findViewById(R.id.EmailEditTextRegister);
        mEditTextPasswordRegister = (EditText) findViewById(R.id.PasswordEditTextRegister);
        btnRegistrarRegister = (Button) findViewById(R.id.BtnRegistarRegister);

        btnRegistrarRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mEditTextNameRegister.getText().toString();
                email = mEditTextEmailRegister.getText().toString();
                password = mEditTextPasswordRegister.getText().toString();

                if(!email.isEmpty() && !password.isEmpty() && !name.isEmpty()){

                    if(password.length() >= 8){

                        UserRegister();
                    }else{

                        Toast.makeText(RegisterActivity.this, "La password debe contener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Toast.makeText(RegisterActivity.this, "Comple los campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void UserRegister(){

        //metodo propio de Firebase autentication
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task -> {

            if(task.isSuccessful()){

                Map<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("email", email);
                map.put("password", password);

                String id = mAuth.getCurrentUser().getUid();

                mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(task1 -> {

                    if(task1.isSuccessful()){
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this, "El usuario no se ha creado corectamente", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{

                Toast.makeText(RegisterActivity.this, "El usuario no se ha registrado corectamente", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    //metodo propio de android
    @Override
    protected void onStart() {
        super.onStart();

        //indicamos que si el usuario ya ha inicado sesión nos rediriga a la la pastalla principal
        if(mAuth.getCurrentUser() != null){

            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }
    }
}