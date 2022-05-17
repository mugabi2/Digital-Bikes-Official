package com.quizinfinity.digitalbikes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private int regStatus=0;
    private String zero="0";
    String name;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String prefName = "userDetails";
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar=findViewById(R.id.pogbal);
        sharedPreferences = this.getSharedPreferences(prefName, MODE_PRIVATE);

        Button loginbtn=findViewById(R.id.btn_login);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText liemail=(EditText)findViewById(R.id.et_emailli);
                EditText lipassword=(EditText)findViewById(R.id.et_passwordli);

                // Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();

                String email = liemail.getText().toString().trim();
                String password = lipassword.getText().toString().trim();
//                Toast.makeText(getApplicationContext(), "push push", Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
//                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("lllll", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    saveDetails(email);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(login.this, "Authentication failed.",Toast.LENGTH_LONG).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });

            }
        });
    }
    public void saveDetails(String email){
//        GET USER DATA FROM FIREBASE
        db.collection("mukusers")
                .whereEqualTo("email",email )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String fname=document.getString("firstname");
                                String sname=document.getString("surname");
                                String phone=document.getString("phone_number");
//                                String email=document.getString("email");
//                                String freeQuizzes=document.getString("email");
                                String fine_status=document.getString("fine_status");
                                String bicycle_out=document.getString("bicycle_out");
                                String bicycle_number=document.getString("bicycle_number");
                                String helmet=document.getString("helmet");
                                String suspension=document.getString("suspension");
                                String password=document.getString("password");
                                String residence=document.getString("residence");
                                String digital_time=document.getString("digital_time");
                                String registration=document.getString("registration");
                                String renting_times=document.getString("renting_times");
                                String free_digital_time=document.getString("free_digital_time");
                                String share_coded=document.getString("share_coded");
                                String preferred_location=document.getString("preferred_location");
                                String earining=document.getString("earining");
                                String gender=document.getString("gender");
                                String date_of_joining=document.getString("date_of_joining");
                                String registered_by=document.getString("registered_by");
                                String fine_times=document.getString("fine_times");
                                String password_recovery=document.getString("password_recovery");
                                String recovery_code=document.getString("recovery_code");
                                String time_riding=document.getString("time_riding");
                                String stars=document.getString("stars");
                                String comment=document.getString("comment");
                                String app_opens=document.getString("app_opens");
                                String profilePhoto=document.getString("profile_photo");
                                String profilePhotoUrl=document.getString("profile_photo_url");

                                Log.d("lllll", date_of_joining);


                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("firstname", fname);
                                editor.putString("surname", sname);
                                editor.putString("phone_number", phone);
                                editor.putString("email", email);
                                editor.putString("fine_status", fine_status);
                                editor.putString("bicycle_out", bicycle_out);
                                editor.putString("bicycle_number", bicycle_number);
                                editor.putString("helmet", helmet);
                                editor.putString("suspension", suspension);
                                editor.putString("password", password);
                                editor.putString("residence", residence);
                                editor.putString("digital_time", digital_time);
                                editor.putString("registration", registration);
                                editor.putString("renting_times", renting_times);
                                editor.putString("free_digital_time", free_digital_time);
                                editor.putString("share_coded", share_coded);
                                editor.putString("preferred_location", preferred_location);
                                editor.putString("earining", earining);
                                editor.putString("gender", gender);
                                editor.putString("date_of_joining", date_of_joining);
                                editor.putString("registered_by", registered_by);
                                editor.putString("fine_times", fine_times);
                                editor.putString("password_recovery", password_recovery);
                                editor.putString("recovery_code", recovery_code);
                                editor.putString("time_riding", time_riding);
                                editor.putString("stars", stars);
                                editor.putString("comment", comment);
                                editor.putString("app_opens", app_opens);
                                editor.putString("profilePhoto", profilePhoto);
                                editor.putString("profilePhotoUrl", profilePhotoUrl);
                                editor.apply();

                                Intent intent=new Intent(login.this,maps.class);
                                finish();
                                startActivity(intent);
                            }
//                            firestoreCallback.onCallback(requestArray);
                        } else {
                            Log.d("milan", "Error getting documents: ", task.getException());
                        }
                    }
                });



    }

    public void createAccount(View view){
        Intent intent= new Intent(this,registration.class);
        startActivity(intent);
    }
}