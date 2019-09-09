package com.example.perabot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class Payment4 extends AppCompatActivity {

    public static final String PAYPAL_KEY = "";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUESR_CODE_FUTURE_PAYMENT = 2;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    Payment4 thingsToBuy;
    Button btnOrder;
    private PayPalConfiguration config;
    private Object Perabot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment4);

        btnOrder = (Button)findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakePayment();
            }
        });
        configPaypal();
    }

    private void configPaypal() {

        config = new PayPalConfiguration()
                .environment(CONFIG_ENVIRONMENT)
                .clientId(PAYPAL_KEY)
                .merchantName("Paypal Login")
                .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
                .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
    }

    private void MakePayment() {

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        thingsToBuy = new Payment4(new BigDecimal(String.valueOf("10.45")), "USD", "Payment",Payment4.PAYMENT_INTENT_SALE);
        Intent payment = new Intent(this, PaymentActivity.class);
        payment.putExtra(PaymentActivity.EXTRA_PAYMENT, (Parcelable) thingsToBuy);
        payment.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startActivityForResult(payment,REQUEST_CODE_PAYMENT);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PAYMENT){
            if(resultCode == Activity.RESULT_OK){
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirm != null){

                    try{
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject().toString(4));

                    }catch (JSONException e){

                        Toast.makeText(this,e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }else if(requestCode == Activity.RESULT_CANCELED){
                Toast.makeText(this,"Payment has been cancelled", Toast.LENGTH_LONG).show();
            }else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
                Toast.makeText(this, "error occurred", Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == REQUESR_CODE_FUTURE_PAYMENT){
            if(resultCode == Activity.RESULT_OK){
                PayPalAuthorization authorization = data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if(authorization != null){
                    try{
                        Log.i("FuturePaymentExample",authorization.toJSONObject().toString(4));
                        String autherization_code = authorization.getAuthorizationCode();
                        Log.d("FuturePaymentExample",autherization_code);
                    }
                }
            }
        }
    }
}
