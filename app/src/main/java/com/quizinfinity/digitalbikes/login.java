package com.quizinfinity.digitalbikes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private int regStatus=0;
    private String zero="0";
    String name;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String prefName = "userDetails";
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;

    // variable for our text input
    // field for phone and OTP.
    private EditText edtPhone, edtOTP;

    // buttons for generating OTP and verifying OTP
    private Button verifyOTPBtn, generateOTPBtn;

    // string for storing our verification ID
    private String verificationId;
    PhoneAuthCredential credential;
    Dialog veryDialog;
    private SmoothProgressBar pogback;
    int one=1,two=2,three=3,four=4,five=5;
    private Interpolator mCurrentInterpolator;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar=findViewById(R.id.pogbal);
        sharedPreferences = this.getSharedPreferences(prefName, MODE_PRIVATE);

        EditText liemail=(EditText)findViewById(R.id.et_emailli);
//                EditText lipassword=(EditText)findViewById(R.id.et_passwordli);

        veryDialog = new Dialog(login.this);
        veryDialog.setContentView(R.layout.verifylogin);

        mCurrentInterpolator = new FastOutSlowInInterpolator();
        pogback=veryDialog.findViewById(R.id.pogback55);
        pogback.setSmoothProgressDrawableSpeed(one);
        pogback.setSmoothProgressDrawableProgressiveStartSpeed(one);
        pogback.setSmoothProgressDrawableProgressiveStopSpeed(one);
        pogback.setSmoothProgressDrawableSectionsCount(five);
        pogback.setSmoothProgressDrawableInterpolator(mCurrentInterpolator);
        pogback.setSmoothProgressDrawableColors(getResources().getIntArray(R.array.dbcolorspock));

        Button loginbtn=findViewById(R.id.btn_login);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // initializing variables for button and Edittext.
                edtOTP = veryDialog.findViewById(R.id.idEdtOtp1);
                verifyOTPBtn = veryDialog.findViewById(R.id.verify1);

                // Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();

                phone = liemail.getText().toString().trim();
                int plen =phone.length();

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Please enter phone number", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if (plen==9) {
                        String phonesend = "+256" + phone;
                        sendVerificationCode(phonesend);
                        veryDialog.setCancelable(true);
                        veryDialog.show();


                    } else {
                        Toast.makeText(getApplicationContext(), "Check length of your phone number", Toast.LENGTH_LONG).show();
                    }

                }
//                if (TextUtils.isEmpty(password)) {
//                    Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                progressBar.setVisibility(View.VISIBLE);
//                mAuth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d("lllll", "signInWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    saveDetails(email);
//
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Log.w("TAG", "signInWithEmail:failure", task.getException());
//                                    Toast.makeText(login.this, "Authentication failed.",Toast.LENGTH_LONG).show();
//                                }
//                                progressBar.setVisibility(View.GONE);
//                            }
//                        });

            }
        });
    }
    public void saveDetails(String phone){
//        GET USER DATA FROM FIREBASE
        db.collection("mukusers")
                .whereEqualTo("phone_number",phone )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String fname=document.getString("firstname");
                                String sname=document.getString("surname");
                                String phone=document.getString("phone_number");
                                String email=document.getString("email");
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

    //************very
    private void signInWithCredential(PhoneAuthCredential credential) {
    }

    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)		 // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)				 // Activity (for callback binding)
                        .setCallbacks(mCallBack)		 // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        Toast.makeText(login.this,"Sending verification code",Toast.LENGTH_SHORT).show();
    }
    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                edtOTP.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        credential = PhoneAuthProvider.getCredential(verificationId, code);

//create user using phone number
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveDetails("0" +phone);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(login.this, "Registration failed.",Toast.LENGTH_SHORT).show();
                            return ;
                        }
                    }
                });
        // after getting credential we are
        // calling sign in method.
//        signInWithCredential(credential);
    }
//************very
}