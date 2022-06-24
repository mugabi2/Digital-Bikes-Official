package com.quizinfinity.digitalbikes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.flutterwave.raveandroid.rave_presentation.RavePayManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class flutterwave extends AppCompatActivity {

    private String prefName = "userDetails";
    SharedPreferences sharedPreferences;
    Button btnOne, btnTwo;
    final int amount_1 = 500;
    final int amount_2 = 1000;
    int amount;
    String currentDT,giveDT,totalDT;
    String email = "samuelmugabi2@gmail.com";
    String fName = "Samuel";
    String lName = "Mugabi";
    String phonay="0774645196";
    String narration = "payment for food";
    String txRef;
    String country = "UG";
    String currency = "UGX";

    final String publicKey = "FLWPUBK-a06274ca7488cff341ae54f21883600f-X"; //Get your public key from your account
    final String encryptionKey = "300c56c4cc3cf1290af704c9"; //Get your encryption key from your account

    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    String pfname, psname,pemail, pphone, pdt,presidence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutterwave);

        sharedPreferences = getSharedPreferences(prefName, MODE_PRIVATE);
        pfname=sharedPreferences.getString("firstname","");
        psname=sharedPreferences.getString("surname","");
        pemail=sharedPreferences.getString("email","");
        pphone=sharedPreferences.getString("phone_number","");
        presidence=sharedPreferences.getString("residence","");
        currentDT=sharedPreferences.getString("digital_time","");

        Bundle extras=getIntent().getExtras();
        amount=extras.getInt("amount");
        makePayment(amount);

    }

    public void backprofile(View view){
        Intent intent=new Intent(flutterwave.this,profile.class);
        startActivity(intent);
        finish();
    }
    public void makePayment(int amount){
        txRef = email +" "+  UUID.randomUUID().toString();


        RavePayManager raveManager;
        raveManager = new RaveUiManager(this)
                .setCountry(country)
                .setCurrency(currency)
                .setAmount(amount)
                .setEmail(pemail)
                .setfName(pfname)
                .setlName(psname)
                .setPhoneNumber(pphone)
                .setNarration(narration)
                .setPublicKey(publicKey)
                .setEncryptionKey(encryptionKey)
                .setTxRef(txRef)
                .acceptUgMobileMoneyPayments(true)
                .onStagingEnv(false);
        raveManager.initialize();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                int totalDtint;
                Map<String, Object> dataOne = new HashMap<>();
//                GIVE DIGITAL TIME
                switch (amount){
                    case 500:
                        giveDT="20";
                        totalDtint=Integer.parseInt(giveDT)+Integer.parseInt(currentDT);
                        totalDT=String.valueOf(totalDtint);

                        dataOne.put("digital_time", totalDT);

                        db.collection("mukusers").document(pphone)
                                .update(dataOne)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("digital_time", totalDT);
                                        editor.apply();
                                        Intent intent = new Intent(flutterwave.this, profile.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("luanda", "Error writing document", e);
                                    }
                                });

                        break;
                    case 1000:
                        giveDT="60";
                        totalDtint=Integer.parseInt(giveDT)+Integer.parseInt(currentDT);
                        totalDT=String.valueOf(totalDtint);

                        dataOne.put("digital_time", totalDT);

                        db.collection("mukusers").document(pphone)
                                .update(dataOne)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("digital_time", totalDT);
                                        editor.apply();
                                        Intent intent = new Intent(flutterwave.this, profile.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("luanda", "Error writing document", e);
                                    }
                                });

                        break;
                    case 10000:
                        giveDT="600";
                        totalDtint=Integer.parseInt(giveDT)+Integer.parseInt(currentDT);
                        totalDT=String.valueOf(totalDtint);

                        dataOne.put("digital_time", totalDT);

                        db.collection("mukusers").document(pphone)
                                .update(dataOne)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("digital_time", totalDT);
                                        editor.apply();
                                        Intent intent = new Intent(flutterwave.this, profile.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("luanda", "Error writing document", e);
                                    }
                                });

                        break;
                }
                Toast.makeText(this, "Thank you for buying Digital Time, enjoy!!!", Toast.LENGTH_LONG).show();
            } else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_SHORT).show();
                Log.i("vvvv", "error "+message);
            } else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
