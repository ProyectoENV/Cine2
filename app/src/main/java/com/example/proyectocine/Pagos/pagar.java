package com.example.proyectocine.Pagos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.proyectocine.Pagos.Config.PayPalConfig;
import com.example.proyectocine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

/*public class pagar extends AppCompatActivity {
    private WebView paypal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar);
        /*paypal = new WebView(pagar.this);
        setContentView(paypal);*//*
        paypal = findViewById(R.id.paypal);
        WebSettings webSettings = paypal.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String url = "www.paypal.com/paypalme/entradasENV";
        paypal.loadUrl(url);


    }
}*/
public class pagar extends AppCompatActivity {
    private static final String TAG = "pagar_activity";

    //CÓDIGO NECESARIO PARA CARGAR LA PANTALLA DE COBRO DE PAYPAL
    private static final int PAYPAL_REQUEST_CODE=7171;

    //SE CONFIGURA EL USO DE PAYPAL PARA HACER PRUEBAS, NO TRANSACCIONES REALES
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(PayPalConfig.PAYPAL_CLIENT_ID);
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private FirebaseUser user= mAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar);
        //INICIA SERVICIO DE PAYPAL
        Intent intent= new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);
        realizarPago("30.25");
    }
    private void realizarPago(String importeCompra) {

        //PASAMOS EL IMPORTE RECIBIDO EN STRING A DECIMALES, ESPECIFICANDO LA MONEDA DE LA TRANSACCIÓN,
        //EN ESTE CASO EUROS, Y EL MENSAJE QUE IDENTIFICA AL COMPRADOR POR EL NICK DEL PERFIL DE USUARIO
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(importeCompra)),
                "EUR","Pago realizado por "+ user.getDisplayName(),PayPalPayment.PAYMENT_INTENT_SALE);

        //SE CARGA EL ENTORNO DE PAYPAL EN MODO DE PRUEBA
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PAYPAL_REQUEST_CODE){
            if(resultCode== RESULT_OK){
                PaymentConfirmation confirmation=data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirmation!=null){

                    try{
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        /*startActivity(new Intent(this, DetailsPaymentActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount",importeCompra)
                                .putExtra("idpedido", pedido));*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }else if(resultCode== Activity.RESULT_CANCELED){ //EN CASO DE CANCELARSE LA TRANSACCIÓN
                Toast.makeText(this,"Error, transacción cancelada",Toast.LENGTH_SHORT).show();
            }
            //EN CASO DE OTRO ERROR, COMO FONDOS INSUFICIENTES
        }else if(resultCode==PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(this,"Error, operación no valida",Toast.LENGTH_SHORT).show();
        }
    }

}
