package com.quizinfinity.digitalbikes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

public class support extends AppCompatActivity {
    MaterialCardView call,rating,tac;
    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        Toolbar toolbar=findViewById(R.id.toolbarsupport);
        setSupportActionBar(toolbar);

        call= findViewById(R.id.callhelp);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int1 =new Intent(Intent.ACTION_DIAL);
                int1.setData(Uri.parse("tel:0774645196"));
                startActivity(int1);
                Log.d("DIAL","CLICKED CALLING-------------");
            }
        });
        tac= findViewById(R.id.emailus);
        tac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://stardigitalbikes.com/terms_and_conditions.php"));
                startActivity(browserIntent);
            }
        });
//send mail*******************************
        email=findViewById(R.id.emailus);
        email.setText(Html.fromHtml("<a href=\"mailto:samuelmugabi2@gmail.com\">Send Feedback</a>"));
        email.setMovementMethod(LinkMovementMethod.getInstance());

        ImageView fb,inst,twit;
        fb=findViewById(R.id.fb);
        inst=findViewById(R.id.inst);
        twit=findViewById(R.id.twit);

        String fburl="https://www.facebook.com/Quiz-Fox-108119678271623";
        String twiturl="https://twitter.com/quizfoxweb";
        String insturl="https://www.instagram.com/quizfoxweb/";
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Facebook", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(fburl));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(support.this, "Instagram", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(insturl));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        twit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Twitter", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(twiturl));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
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
                startActivity(new Intent(support.this, profile.class));
                break;
            case R.id.action_safetytips:
                startActivity(new Intent(support.this, safetyTips.class));
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
}