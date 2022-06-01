package com.quizinfinity.digitalbikes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class profile extends AppCompatActivity {

    private String prefName = "userDetails";
    SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    String pfname, psname,pemail, pphone, pdt,presidence;
    TextView tname,temail,tphone,tdt,tresidence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences(prefName, MODE_PRIVATE);
        mAuth=FirebaseAuth.getInstance();
        Toolbar toolbar=findViewById(R.id.toolbarprofile);
        setSupportActionBar(toolbar);

        pfname=sharedPreferences.getString("firstname","");
        psname=sharedPreferences.getString("surname","");
        pemail=sharedPreferences.getString("email","");
        pphone=sharedPreferences.getString("phone_number","");
        presidence=sharedPreferences.getString("residence","");
        pdt=sharedPreferences.getString("digital_time","");

        tname=findViewById(R.id.textinname);
        temail=findViewById(R.id.textinemail);
        tphone=findViewById(R.id.textinphone);
        tresidence=findViewById(R.id.textinresidence);
        tdt=findViewById(R.id.textindt);

        tname.setText(pfname +" "+psname);
        temail.setText(pemail);
        tphone.setText(pphone);
        tresidence.setText(presidence);
        tdt.setText(pdt);


        Log.i("ppppp",pfname+"  "+pphone);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.fresh_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()){
            case R.id.action_profile:
                startActivity(new Intent(profile.this, profile.class));
                break;
            case R.id.action_safetytips:
                startActivity(new Intent(profile.this, safetyTips.class));
                break;
            case R.id.action_termsandconds:
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://stardigitalbikes.com/terms_and_conditions.php"));
                startActivity(browserIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signMeOut(View view){
        sharedPreferences.edit().clear().apply();

        mAuth.getInstance().signOut();
        Intent intent = new Intent(profile.this,login.class);
        startActivity(intent);

    }}