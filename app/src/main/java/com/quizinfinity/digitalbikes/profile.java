package com.quizinfinity.digitalbikes;

import static com.quizinfinity.digitalbikes.mymethods.decodeBase64;
import static com.quizinfinity.digitalbikes.mymethods.encodeTobase64;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.flutterwave.raveandroid.rave_presentation.RavePayManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

public class profile extends AppCompatActivity {

    private String prefName = "userDetails";
    SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    String pfname, psname,pemail, pphone, pdt,presidence;
    TextView tname,temail,tphone,tdt,tresidence;
    private CircularImageView imageView,nid;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;//Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    String zero="0",email;
    Dialog dialogEdit,dialogdtpop;
    EditText esname,efname,ephone,eemail,epassword,eresi,esharecode;
    String nsname, nfname, nphone, nemail, npsword, nresid;
    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    Button btnOne, btnTwo;
    final int pricehourglass = 1000;
    final int pricetimebucket = 10000;
    Button dtbuy;
    LinearLayout dtpop;
    FrameLayout fhourglass,ftimebucket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        sharedPreferences = getSharedPreferences(prefName, MODE_PRIVATE);
        mAuth=FirebaseAuth.getInstance();
        Toolbar toolbar=findViewById(R.id.toolbarprofile);
        setSupportActionBar(toolbar);
        dialogEdit = new Dialog(this);
        dialogEdit.setContentView(R.layout.edit_details);
        esname= dialogEdit.findViewById(R.id.sname);
        efname= dialogEdit.findViewById(R.id.fname);
        eemail= dialogEdit.findViewById(R.id.Eemail);
        eresi= dialogEdit.findViewById(R.id.resi);

        dialogdtpop = new Dialog(this);
        dialogdtpop.setContentView(R.layout.digital_time);
        fhourglass= dialogdtpop.findViewById(R.id.hourglass);
        ftimebucket= dialogdtpop.findViewById(R.id.timebucket);

//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("firstname", "John");
//        editor.putString("surname", "Museveni");
//        editor.putString("phone_number", "0774645196");
//        editor.putString("email", "samuel@chocolatecosmosweb.com");
//        editor.putString("digital_time", "20");
//        editor.putString("residence", "katende");
//        editor.apply();

        dtpop=findViewById(R.id.digitaltime);
        dtpop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogdtpop.show();
            }
        });

        fhourglass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(profile.this,flutterwave.class);
                intent.putExtra("amount",500);
                startActivity(intent);
            }
        });

        ftimebucket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(profile.this,flutterwave.class);
                intent.putExtra("amount",pricetimebucket);
                startActivity(intent);
            }
        });

        imageView=findViewById(R.id.profilephoto);
        pfname=sharedPreferences.getString("firstname","");
        psname=sharedPreferences.getString("surname","");
        pemail=sharedPreferences.getString("email","");
        pphone=sharedPreferences.getString("phone_number","");
        presidence=sharedPreferences.getString("residence","");
        pdt=sharedPreferences.getString("digital_time","");
        String bitmapString=sharedPreferences.getString("profile_picture","");
        Log.d("kkkkk",bitmapString);

        if (!bitmapString.isEmpty()) {
            imageView.setImageBitmap(decodeBase64(bitmapString));
        }
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

    }

    public void chooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profile_picture", encodeTobase64(bitmap));
                editor.commit();
                uploadImage();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    public void uploadImage() {

//        if(filePath != null)
//        {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference ref = storageReference.child("userProfilePhotos/"+ pphone);
        ref.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(profile.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(photos.this,nationalId.class);
//                        finish();
//                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(profile.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
                    }
                });
//        }
    }

    public void edit(View view){
        dialogEdit.setCancelable(true);
        dialogEdit.show();

        esname.setText(pfname);
        efname.setText(psname);
        eemail.setText(pemail);
        eresi.setText(presidence);
//        tdt.setText(pdt);

    }
    public void registerDetails(View view){
        String today=getCurrentDate();
        nsname = esname.getText().toString();
        nfname = efname.getText().toString();
        nemail = eemail.getText().toString();
        nresid = eresi.getText().toString();

        Toast.makeText(profile.this, nsname+" "+nfname+" "+nemail+" "+nresid,Toast.LENGTH_SHORT).show();

        Map<String, Object> dataOne = new HashMap<>();
        dataOne.put("firstname", nfname);
        dataOne.put("surname", nsname);
        dataOne.put("email", nemail);
        dataOne.put("residence", nresid);
        dataOne.put("preferred_location", "MUK");
        dataOne.put("date_of_editing", today);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstname", nfname);
        editor.putString("surname", nsname);
        editor.putString("email", nemail);
        editor.putString("residence", nresid);
        editor.putString("preferred_location", "MUK");
        editor.putString("date_of_editing", today);
        editor.apply();

        db.collection("mukusers").document(pphone)
                .update(dataOne)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(profile.this, profile.class);
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
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EAT"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

}