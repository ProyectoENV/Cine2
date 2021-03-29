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

public class ActivityLogin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button btnLogin;
    private Button btnRegistrar;

    //variables datos
    private String email = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //instanciamos la base de datos y los campos
        mAuth = FirebaseAuth.getInstance();
        mEditTextEmail = (EditText) findViewById(R.id.EmailEditTextLogin);
        mEditTextPassword = (EditText) findViewById(R.id.PasswordEditTextLogin);
        btnLogin = (Button) findViewById(R.id.BtnLogin);
        btnRegistrar = (Button) findViewById(R.id.BtnRegistartLogin);

        //Cuando pulasmos el boton de login nos redirige a la principal con los datos del usuario
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEditTextEmail.getText().toString();
                password = mEditTextPassword.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){

                    UserLogin();
                }else{

                    Toast.makeText(ActivityLogin.this, "Comple los campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //cuando pulsamos el boton de registar nos redirige al RegisterActivity para poder registrar el usuario
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ActivityLogin.this, RegisterActivity.class));
            }
        });
    }

    private void UserLogin(){

        //metodo propio de Firebase autentication
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    startActivity(new Intent(ActivityLogin.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(ActivityLogin.this, "No se ha podido iniciar sesion, compruebe que los datos son correctos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}