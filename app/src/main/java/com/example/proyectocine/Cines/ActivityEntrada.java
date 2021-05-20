package com.example.proyectocine.Cines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.LocalPayment;
import com.braintreepayments.api.PayPal;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.models.Authorization;
import com.braintreepayments.api.models.LocalPaymentRequest;
import com.braintreepayments.api.models.PayPalAccountNonce;
import com.braintreepayments.api.models.PayPalRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.api.models.PostalAddress;
import com.example.proyectocine.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Transaction;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ActivityEntrada extends AppCompatActivity {

    //bbdd
    private DatabaseReference mDataBase;

    //Variables para recibir intent
    private String id_pelicula_entrada;
    private int id_cine_entrada;
    private int id_sala_entrada;
    private String hora_entrada;
    private ArrayList<String> id_asientos_reservados;
    private String Lista_ocupados_mas_reservas;
    private  String id_tabla;

    //Layout
    private TextView cine ;
    private TextView pelicula ;
    private TextView sala ;
    private TextView hora ;
    private TextView fila ;
    private TextView columna ;
    private Button pagar ;

    //cosas pagar
    private BraintreeFragment mBraintreeFragment ;
    private Authorization mAuthorization ;
    private String  mClientToken = "Token";
    private      int DROP_IN_REQUEST = 1;
    private      int REQUEST_CODE = 2048;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);

        mDataBase = FirebaseDatabase.getInstance().getReference();

        cine = findViewById(R.id.NombreCine);
        pelicula = findViewById(R.id.Nombrepel);
        sala = findViewById(R.id.Sala);
        hora = findViewById(R.id.Hora);
        fila = findViewById(R.id.Fila);
        columna = findViewById(R.id.Columna);
        pagar = findViewById(R.id.entradas_seleccionadas_boton);


        //insercion de los asientos ocupados
        /*Map<String, Object> map = new HashMap<>();
        map.put("/asientos_ocupados/", id_reserva);
        mDataBase.child("Peli_cine_entrada_hora").child(id_tabla).updateChildren(map);*/
        Intent recibir_datos = getIntent();
        Bundle traer_datos = recibir_datos.getExtras();
        if(recibir_datos.hasExtra("id_cine_e")){
            id_cine_entrada= Integer.parseInt(traer_datos.get("id_cine_e").toString());
            id_pelicula_entrada = traer_datos.getString("id_pelicula");
            id_sala_entrada = Integer.parseInt(traer_datos.get("id_sala_e").toString());
            hora_entrada = traer_datos.getString("hora_e");
            id_asientos_reservados = traer_datos.getStringArrayList("asientos_reservados_string_e");
            Lista_ocupados_mas_reservas = traer_datos.getString("asientos_ocupados_mas_reservas");
        }
        String filas="";
        String Columnas = "";
        for (String entrada:id_asientos_reservados) {
            String [] fila_columna =entrada.split("_");
            filas = filas+(Integer.parseInt(fila_columna[0])+1)+", ";
            Columnas = Columnas+(Integer.parseInt(fila_columna[1])+1)+", ";

        }

        buscar_cine(id_cine_entrada);
        buscar_pelicula(id_pelicula_entrada);

        sala.setText(id_sala_entrada+"");
        hora.setText(hora_entrada);
        fila.setText(filas);
        columna.setText(Columnas);
        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Map<String, Object> map = new HashMap<>();
                map.put("/asientos_ocupados/", Lista_ocupados_mas_reservas);
                mDataBase.child("Peli_cine_entrada_hora").child(id_tabla).updateChildren(map);*/


                try {
                    onBraintreeSubmit();
                } catch (InvalidArgumentException e) {
                    e.printStackTrace();
                }


            }
        });

    }
    public void onBraintreeSubmit() throws InvalidArgumentException {

        DropInRequest dropInRequest = new DropInRequest()
                .tokenizationKey("sandbox_ykqb274b_mvff8qnfg33dynj6");



        mBraintreeFragment = BraintreeFragment.newInstance(ActivityEntrada.this, dropInRequest.getAuthorization());
        double total = (id_asientos_reservados.size()*8.35);
        PayPalRequest requestP = new PayPalRequest(total+"")
                .currencyCode("EUR")
                .intent(PayPalRequest.INTENT_AUTHORIZE);
        //PayPal.requestOneTimePayment(mBraintreeFragment, requestP);
        dropInRequest.paypalRequest(requestP);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Pagotest", resultCode+" "+RESULT_OK);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                Log.d("Pagotest", "onactivityresult bien");
                //onPaymentMethodNonceCreated(result.getPaymentMethodNonce());



                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == RESULT_CANCELED) {
                // the user canceled
                Log.d("Pagotest", "onactivityresult fallo ");
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("Pagotest",error.getMessage());
            }
        }
    }


    private void buscar_pelicula(String id_pelicula_entrada) {
        String[] nombre = new String[1];
        mDataBase.child("Pelicula").child(id_pelicula_entrada).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String nombre_peli = (String) snapshot.child("nombre").getValue();
                    nombre[0] = nombre_peli;
                    pelicula.setText(nombre[0]);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    private void buscar_cine(int id_cine_entrada) {
        String[] nombre = new String[1];
        mDataBase.child("Peli_cine_sala_hora").orderByChild("id_cine").equalTo(id_cine_entrada).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String nombre_cine = (String) ds.child("nombre_cine").getValue();
                   nombre[0] = nombre_cine;
                    cine.setText(nombre[0]);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}