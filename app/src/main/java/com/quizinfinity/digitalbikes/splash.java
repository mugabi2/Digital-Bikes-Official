package com.quizinfinity.digitalbikes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class splash extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String rent,email,phone;
    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    private String prefName = "userDetails";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = this.getSharedPreferences(prefName, MODE_PRIVATE);
        phone=sharedPreferences.getString("phone_number","");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if user is signed in (non-null) and update UI accordingly.
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser != null){

//        GET USER DATA FROM FIREBASE
                    db.collection("mukusers")
                            .whereEqualTo("phone_number",phone )
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            rent=document.getString("has_rented");
                                        }
//                            firestoreCallback.onCallback(requestArray);
                                    } else {
                                        Log.d("milan", "Error getting documents: ", task.getException());
                                    }
                                    Intent intent=new Intent(splash.this,maps.class);
                                    intent.putExtra("has_rented",rent);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                }else{
                    Intent intent=new Intent(splash.this,login.class);
                    startActivity(intent);
                    finish();
                }
//                TODO SPLASH SOME
            }
        }, 3000);
    }
    @Override
    public void onStart() {
        super.onStart();
    }
}