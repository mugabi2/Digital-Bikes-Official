package com.quizinfinity.digitalbikes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
//import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


public class registration extends AppCompatActivity{

    private ProgressBar progBar;
    private TextView text;
    private Handler mHandler = new Handler();
    private int mProgressStatus=0;

    private SharedPreferences prefer;
    private String prefName2 ="preBike";
    String thecode="0",send;

    String serverKey="2y10f2Kkl1GRi5si0AAsgvsgJWyqXsUszC3DuvRLwZZ";
    private static final String SURNAME_KEY ="Surname";
    private static final String FIRST_NAME_KEY ="First Name";
    private static final String PHONE_NUMBER_KEY ="Phone Number";
    private static final String EMAIL_ADDRESS_KEY ="Email";
    private static final String RESIDENCE_KEY ="Residence";
    private static final String GENDER_KEY ="Gender";
    private static final String LOCATION_KEY ="Location";
    private static final String LOGIN_STATUS_KEY ="Login Status";

    private SharedPreferences prefs,prefl;
    private String preflogin="preflogin";
    String usersurname,userfirstname,userphonenumb,useremailadd,userresidence,usergender,message,preferred,prefer2;

    Dialog shareDialog, tacDialog, veryDialog;



    String sex="M",mesg,all,msg;
    EditText esname,efname,ephone,eemail,epassword,eresi,esharecode;
    Button Bsignup,Bcontinueshare;
    int suckinda,sucks;

    //    SPINNER
    private Spinner spinner;
    private static final String[] paths = {"MUK"};//, "MUBS", "UCU"};

    CheckBox checkBoxer;
    int tac=0;

    String promotion;
    ProgressBar pogba;

    private SmoothProgressBar pogback;
    int one=1,two=2,three=3,four=4,five=5;
    private Interpolator mCurrentInterpolator;

    String sname, fname, phone, email, psword, resid;

    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    PhoneAuthCredential credential;
    String zero="0", phonel;
    private String prefName = "userDetails";
    SharedPreferences sharedPreferences;



    // variable for our text input
    // field for phone and OTP.
    private EditText edtPhone, edtOTP;

    // buttons for generating OTP and verifying OTP
    private Button verifyOTPBtn, generateOTPBtn;

    // string for storing our verification ID
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = this.getSharedPreferences(prefName, MODE_PRIVATE);

        pogba=findViewById(R.id.pogba5);
        tacDialog = new Dialog(this);
        tacDialog.setContentView(R.layout.terms_and_conditions);
        veryDialog = new Dialog(this);
        veryDialog.setContentView(R.layout.verily);

        pogback=veryDialog.findViewById(R.id.pogback55);

        mCurrentInterpolator = new FastOutSlowInInterpolator();

        pogback.setSmoothProgressDrawableSpeed(one);
        pogback.setSmoothProgressDrawableProgressiveStartSpeed(one);
        pogback.setSmoothProgressDrawableProgressiveStopSpeed(one);
        pogback.setSmoothProgressDrawableSectionsCount(five);
        pogback.setSmoothProgressDrawableInterpolator(mCurrentInterpolator);
        pogback.setSmoothProgressDrawableColors(getResources().getIntArray(R.array.dbcolorspock));
//        pogback.setSmoothProgressDrawableMirrorMode(true);
//        pogback.setSmoothProgressDrawableSeparatorLength(dpToPx(mSeparatorLength));
//        STATUS BAR
        if(Build.VERSION.SDK_INT >=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.darkdarkTurq));
        }

        preferred="MUK";


//        checkBoxer=findViewById(R.id.check);
//        checkBoxer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("check", "cheque "+tac);
////                Toast.makeText(getApplicationContext(),"check "+tac,Toast.LENGTH_SHORT).show();
//                if(checkBoxer.isChecked()){
//                    tac=1;
//                }else{
//                    tac=0;
//                }
//            }
//        });

        Button bsinu=findViewById(R.id.signup);
//        ANIMATION
        Animation animation= AnimationUtils.loadAnimation(registration.this,R.anim.blink_anim);
        bsinu.startAnimation(animation);

        //^^^^^^^^^^^^^^^^^^^^^^
//        spinner = (Spinner)findViewById(R.id.spinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(registration.this,
//                android.R.layout.simple_spinner_item,paths);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);


//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

        shareDialog = new Dialog(this);
        shareDialog.setContentView(R.layout.share_code);

//        pogless();

        esname= findViewById(R.id.sname);
        efname= findViewById(R.id.fname);
        ephone= findViewById(R.id.phonenum);
        eemail= findViewById(R.id.Eemail);
        epassword= findViewById(R.id.Ppassword);
        eresi= findViewById(R.id.resi);
        Bsignup= findViewById(R.id.signup);

//*************very

        // initializing variables for button and Edittext.
        edtOTP = veryDialog.findViewById(R.id.idEdtOtp);
        verifyOTPBtn = veryDialog.findViewById(R.id.verify);
//        generateOTPBtn = findViewById(R.id.idBtnGetOtp);
//
//        // setting onclick listener for generate OTP button.
//        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    // if the text field is not empty we are calling our
//                    // send OTP method for getting OTP from Firebase.
//                    String phone = "+256" + phonel;
//                    sendVerificationCode(phone);
//            }
//        });

        // initializing on click listener
        // for verify otp button
        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the OTP text field is empty or not.
                if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                    // if the OTP text field is empty display
                    // a message to user to enter OTP
                    Toast.makeText(registration.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    verifyCode(edtOTP.getText().toString());
                }
            }
        });
//        *********very
    }



    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radio_male:
                if (checked){
                    sex="M";
                }
                break;
            case R.id.radio_female:
                if (checked){
                    sex="F";
                }
                break;
        }
    }
    public void popme(View view) {
        phonel =ephone.getText().toString();
        Button tacb,proceed;
        TextView upmsg;
        tacb=tacDialog.findViewById(R.id.tacpop);
        tacb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://stardigitalbikes.com/terms_and_conditions.php"));
                startActivity(browserIntent);
            }
        });
//        proceed=tacDialog.findViewById(R.id.proceedpop);
//        proceed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tacDialog.dismiss();
//            }
//        });

        int plen =phonel.length();

        sname = esname.getText().toString();
        fname = efname.getText().toString();
        phone = ephone.getText().toString();
        email = eemail.getText().toString();
        psword= epassword.getText().toString();
        resid = eresi.getText().toString();

        //Checking if all fields have been filled
        if (!sname.isEmpty() && !fname.isEmpty() && !phone.isEmpty() && !psword.isEmpty()
                && !email.isEmpty() && !resid.isEmpty()) {

            if (plen==9) {
                String phone = "+256" + phonel;
            sendVerificationCode(phone);
            veryDialog.setCancelable(true);
            veryDialog.show();

        } else {
            Toast.makeText(getApplicationContext(), "Check length of your phone number", Toast.LENGTH_LONG).show();
        }
        }else {
                Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_LONG).show();
            }

    }

    public void TACwebsite(View view){
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://stardigitalbikes.com/terms_and_conditions.php"));
        startActivity(browserIntent);
    }

    public void pogless() {

        new Thread(new Runnable() {
            public void run() {
                final int presentage=0;
                while (mProgressStatus < 100) {
                    mProgressStatus += 10;
                    if(mProgressStatus==100){
                        mProgressStatus=0;
                    }
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
//                            text.setText(""+mProgressStatus+"%");
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void regMe(View view){

//                if (tac==1) {
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
                                    registerDetails();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(registration.this, "Registration failed.",Toast.LENGTH_SHORT).show();
                                    return ;
                                }
                    }
                });


                //create user using email
//                mAuth.createUserWithEmailAndPassword(email, psword)
//                        .addOnCompleteListener(registration.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d("TAG", "createUserWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    registerDetails();
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
//                                    Toast.makeText(registration.this, "Registration failed.",Toast.LENGTH_SHORT).show();
//                                    return ;
//                                }
////                                progressBar.setVisibility(View.GONE);
//                            }
//                        });

    }
    //##################BACK GROUND CLASSS$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$444444444
    public void registerDetails(){
        phone=0+phone;
        String today=getCurrentDate();
        Map<String, Object> dataOne = new HashMap<>();
        dataOne.put("firstname", fname);
        dataOne.put("surname", sname);
        dataOne.put("phone_number", phone);
        dataOne.put("email", email);
        dataOne.put("fine_status", "0");
        dataOne.put("bicycle_out", "0");
        dataOne.put("bicycle_number", "0");
        dataOne.put("helmet", "0");
        dataOne.put("suspension", "0");
        dataOne.put("password", psword);
        dataOne.put("residence", resid);
        dataOne.put("digital_time", "00:20");
        dataOne.put("registration", "0");
        dataOne.put("renting_times", "0");
        dataOne.put("rent", "0");
        dataOne.put("log_in_times", "1");
        dataOne.put("free_digital_time", "00:00");
        dataOne.put("share_coded", "0");
        dataOne.put("preferred_location", "MUK");
        dataOne.put("earining", "0");
        dataOne.put("gender", sex);
        dataOne.put("date_of_joining", today);
        dataOne.put("registered_by", "0");
        dataOne.put("fine_times", "0");
        dataOne.put("password_recovery", "0");
        dataOne.put("recovery_code", "0");
        dataOne.put("time_riding", "00:00");
        dataOne.put("stars", "5");
        dataOne.put("comment", "five star");
        dataOne.put("app_opens", "1");
        dataOne.put("profilePhoto", zero);
        dataOne.put("profilePhotoUrl", "gs://quiz-fox.appspot.com/USERS/default.jpg");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstname", fname);
        editor.putString("surname", sname);
        editor.putString("phone_number", phone);
        editor.putString("email", email);
        editor.putString("fine_status", "0");
        editor.putString("bicycle_out", "0");
        editor.putString("bicycle_number", "0");
        editor.putString("helmet", "0");
        editor.putString("suspension", "0");
        editor.putString("password", psword);
        editor.putString("residence", resid);
        editor.putString("digital_time", "00:20");
        editor.putString("registration", "0");
        editor.putString("renting_times", "0");
        editor.putString("rent", "0");
        editor.putString("log_in_times", "1");
        editor.putString("free_digital_time", "00:00");
        editor.putString("share_coded", "0");
        editor.putString("preferred_location", "MUK");
        editor.putString("earining", "0");
        editor.putString("gender", sex);
        editor.putString("date_of_joining", today);
        editor.putString("registered_by", "0");
        editor.putString("fine_times", "0");
        editor.putString("password_recovery", "0");
        editor.putString("recovery_code", "0");
        editor.putString("time_riding", "00:00");
        editor.putString("stars", "5");
        editor.putString("comment", "five star");
        editor.putString("rent", "0");
        editor.putString("profile_photo", zero);
        editor.putString("profile_photo_url", "gs://quiz-fox.appspot.com/USERS/default.jpg");
        editor.apply();

        promotion="0";
        Log.d("sxs", "REGITSTRATION without promo");
        db.collection("mukusers").document(phone)
                .set(dataOne)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(registration.this, intro.class);
                        finish();
                        startActivity(intent);
                        Log.d("luanda", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("luanda", "Error writing document", e);
                    }
                });

// Add a new document with a generated ID

    }

    public void logMeIn(View view){
        Intent intent= new Intent(this,login.class);
        startActivity(intent);
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EAT"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    public void checkboxed(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        Toast.makeText(getApplicationContext(),"touch touch touch",Toast.LENGTH_SHORT).show();
//        !!!
        if (checked){
            Toast.makeText(getApplicationContext(),"check",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Please check terms and conditions",Toast.LENGTH_SHORT).show();
        }
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
        Toast.makeText(registration.this,"Sending verification code",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(registration.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        credential = PhoneAuthProvider.getCredential(verificationId, code);
        Bsignup.setClickable(true);
        // after getting credential we are
        // calling sign in method.
//        signInWithCredential(credential);
    }
//************very
}
