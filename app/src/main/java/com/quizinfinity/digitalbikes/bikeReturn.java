package com.quizinfinity.digitalbikes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class bikeReturn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_return);

        Toolbar toolbar=findViewById(R.id.toolbarreturn);
        setSupportActionBar(toolbar);
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
                startActivity(new Intent(bikeReturn.this, profile.class));
                break;
            case R.id.action_safetytips:
                startActivity(new Intent(bikeReturn.this, safetyTips.class));
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
    public void sha(View view){
//SHARE CODE
//            progBar.setVisibility(ProgressBar.VISIBLE);
        Intent int1 =new Intent(Intent.ACTION_SEND);
        int1.setType("text/plain");
        String shareBody ="https://play.google.com/store/apps/details?id=com.stardigitalbikes.digitalbikes" +
                " Follow the link above to download Digital Bikes and earn 20 minutes Digital time";
//            String shareBody =getResources().getString(R.string.mylink)+gencode;
        int1.putExtra(Intent.EXTRA_SUBJECT,shareBody);
        int1.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(int1, "Share using"));

    }
    public void sup(View view){
        startActivity(new Intent(bikeReturn.this, support.class));//support
    }
    public void ins(View view){
        Intent int111=new Intent(bikeReturn.this,intro.class);
//        int111.putExtra("bikesin",ups);
        startActivity(int111);
//        startActivity(new Intent(Mapsimport1.this, appIntro.class));//Instructions// credit for progress/lottie
    }
}