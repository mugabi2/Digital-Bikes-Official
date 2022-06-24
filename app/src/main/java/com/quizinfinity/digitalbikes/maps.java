package com.quizinfinity.digitalbikes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import android.widget.RadioButton;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.quizinfinity.digitalbikes.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.quizinfinity.digitalbikes.registration.getCurrentDate;

public class maps extends  AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener{

        private GoogleMap mMap;
        LatLng zooom;
        String location="MUK";
    final List<MarkerOptions> markers = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference dbref = db.document("mukbvs/bikes");
    String pkafrica, pkcedat, pkcomplex, pkfema, pklibrary, pklivingstone, pklumumba, pkmaingate, pkmarystuart, pkmitchell, pknkrumah, pkuh;
    String pktpark,pknsibambi,pksabiiti,pkdhucu,pklibraryucu,pkpeggynoll,pkwandegenya,pkmaingateucu,pkbtucker;
    int availableBikes;
    private ActivityMapsBinding binding;

    Dialog myDialog, updialog,ratdialog;
    String usname, ufname, uphone, umail, uresi, udura = "20", upaymeth, uagcode,ustation,ustationcode,prx="0",ditime,duration;
    TextView durationtext;
    SeekBar seekduration;
    int min = 0, max = 5, current = 0;
    String paymentInt="cash";
    int checkPm = 0, pmi, pmc = 1, pmd = -1, suckind, succfour, sucki;

//    BOTTOM SHEET PRICE
    String ExternalFontPath;
    Typeface FontLoaderTypeface;
    BottomSheetBehavior mBottomSheetBehavior,mbottomSheetBehavior1;
    Button mButton1;
    ImageView downArrow;
    LinearLayout upBar;
    BottomSheetBehavior mBottomSheetBehaviour;;
    private String prefName = "userDetails";
    SharedPreferences sharedPreferences;
    String rent,rentfresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar=findViewById(R.id.toolbarmaps);
        setSupportActionBar(toolbar);

        Bundle extras=getIntent().getExtras();
//        rent=extras.getString("has_rented");

        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.rent_bike_popup);//rent_bike_popup

        sharedPreferences = this.getSharedPreferences(prefName, MODE_PRIVATE);
        umail=sharedPreferences.getString("email","");
        usname=sharedPreferences.getString("surname","");
        ufname=sharedPreferences.getString("firstname","");
        uphone=sharedPreferences.getString("phone_number","");
        uresi=sharedPreferences.getString("residence","");
        ditime=sharedPreferences.getString("digital_time","");


        dbref.addSnapshotListener(maps.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(maps.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (documentSnapshot.exists()) {
                    pkafrica = documentSnapshot.getString("Africa");
                    Log.i("%%%%",pkafrica);
                    pkcedat = documentSnapshot.getString("CEDAT");
                    pkcomplex = documentSnapshot.getString("Complex");
                    pkfema = documentSnapshot.getString("FEMA");
                    pklibrary = documentSnapshot.getString("Library");
                    pklivingstone = documentSnapshot.getString("Livingstone");
                    pklumumba = documentSnapshot.getString("Lumumba");
                    pkmaingate = documentSnapshot.getString("Main Gate");
                    pkmarystuart = documentSnapshot.getString("Marystuart");
                    pkmitchell = documentSnapshot.getString("Mitchell");
                    pknkrumah = documentSnapshot.getString("Nkrumah");
                    pkuh = documentSnapshot.getString("University Hall");
                }
            }
        });

        DocumentReference dbrent = db.document("mukusers/"+uphone);
        dbrent.addSnapshotListener(maps.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(maps.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (documentSnapshot.exists()) {
                    rentfresh = documentSnapshot.getString("has_rented");
                    if(rentfresh.equals("yes")){
                        pkafrica= pkcedat= pkcomplex= pkfema= pklibrary= pklivingstone= pklumumba= pkmaingate
                                =pkmarystuart= pkmitchell= pknkrumah= pkuh="Return Bike";
                        Log.i("11111f", pkafrica);

                        Toast.makeText(maps.this, "Please select location to return bike.", Toast.LENGTH_LONG).show();
                    }else {
                        Log.i("11111f", "rentfresh");}
                }
            }
        });

        switch (location){
            case "MUK":
//                zooom= new LatLng(0.331604, 32.zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz568423);
//                Toast.makeText(getApplicationContext(),"Makerere University",Toast.LENGTH_LONG).show();
                break;
            case "MUBS":
                zooom= new LatLng(0.111111, 20.568423);
//                Toast.makeText(getApplicationContext(),"Makerere Business School",Toast.LENGTH_LONG).show();
                break;
            case "UCU":
                zooom= new LatLng(0.355084, 32.740389);
//                Toast.makeText(getApplicationContext(),"Uganda Christian University",Toast.LENGTH_LONG).show();
                break;
        }
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


//        ------------------------------------
////        BOTTOM SHEET PRICE

        View bottomSheet = findViewById(R.id.bottom_sheetid);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

//        TextView buttonShow = findViewById(R.id.expand1);
        RelativeLayout buttonShow = findViewById(R.id.expand1);
        downArrow = findViewById(R.id.down_arrow1);
        upBar = findViewById(R.id.up_bar1);
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {if(mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                downArrow.setVisibility(View.INVISIBLE);
                upBar.setVisibility(View.VISIBLE);
//                new Mapsimport1.downloadimage().execute();

            }
            else {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                downArrow.setVisibility(View.VISIBLE);
                upBar.setVisibility(View.INVISIBLE);
            }
            }
        });
//        ImageView shareimg=(ImageView)findViewById(R.id.imageshare);
//        ImageView supimg=(ImageView)findViewById(R.id.imagesupport);
//        ImageView instimg=(ImageView)findViewById(R.id.imageinst);
//
//        shareimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Mapsimport1.this, GoPremium.class));// CHANGE BACK TO PROMOTIONS
//            }
//        });
//
//        instimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startActivity(new Intent(Mapsimport1.this, Instructions.class));
//                showFeedback();
//            }
//        });
//
//        supimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Mapsimport1.this, Support.class));
//            }
//        });

        View bottomSheete = findViewById(R.id.bottom_sheetid);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheete);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
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
                startActivity(new Intent(maps.this, profile.class));
                break;
            case R.id.action_safetytips:
                startActivity(new Intent(maps.this, safetyTips.class));
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
        startActivity(new Intent(maps.this, support.class));//support
    }
    public void ins(View view){
        Intent int111=new Intent(maps.this,intro.class);
//        int111.putExtra("bikesin",ups);
        startActivity(int111);
//        startActivity(new Intent(Mapsimport1.this, appIntro.class));//Instructions// credit for progress/lottie
    }
    public void TACwebsite(View view){
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://stardigitalbikes.com/terms_and_conditions.php"));
        startActivity(browserIntent);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(maps.this));
        mMap.setOnInfoWindowClickListener(this);
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            bulidGoogleApiClient();
//            mMap.setMyLocationEnabled(true);
//        }
////zzzzzzzzzzzzzzzoooooooooooooooooooommmmmmmmmmmmmmmmmmmmmmm
////       THESE(ZOOM VARIABLES) WILL BE TURNED INTO VARIABLES E.G FREEDOM
////        USE THE LOCATION OF THE USER TO DETERMINE
////                  1***WHERE TO ZOOM IN
////                  2***WHICH MARKER TO OPEN WITH
        float zom= (float) 15.5;
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0.331604, 32.568423),zom));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0.334561, 32.569160),zom));

        String tit;
        LatLng africa = new LatLng(0.337912, 32.568790);
        tit="Africa";
        showInfoWindow(africa,tit,pkafrica);
        LatLng cedat = new LatLng(0.335882, 32.564807);
        tit="CEDAT"; showInfoWindow(cedat,tit,pkcedat);
        LatLng complex = new LatLng(0.329849, 32.570160);
        tit="Complex"; showInfoWindow(complex,tit,pkcomplex);
        LatLng fema = new LatLng(0.335345, 32.568673);
        tit="FEMA"; showInfoWindow(fema,tit,pkfema);
        LatLng library = new LatLng(0.334936, 32.568000);
        tit="Library"; showInfoWindow(library,tit,pklibrary);
        LatLng livingstone = new LatLng(0.338686, 32.567718);
        tit="Livingstone"; showInfoWindow(livingstone,tit,pklivingstone);
        LatLng lumumba = new LatLng(0.331717, 32.566073);
        tit="Lumumba"; showInfoWindow(lumumba,tit,pklumumba);
        LatLng maingate = new LatLng(0.329760, 32.570937);
        tit="Main Gate"; showInfoWindow(maingate,tit,pkmaingate);
        LatLng marystuart = new LatLng(0.330985, 32.566668);
        tit="Marystuart"; showInfoWindow(marystuart,tit,pkmarystuart);
        LatLng mitchell = new LatLng(0.333740, 32.570495);
        tit="Mitchell"; showInfoWindow(mitchell,tit,pkmitchell);
        LatLng nkrumah = new LatLng(0.336454, 32.569008);
        tit="Nkrumah"; showInfoWindow(nkrumah,tit,pknkrumah);
        LatLng uh = new LatLng(0.332969, 32.572506);
        tit="University Hall"; showInfoWindow(uh,tit,pkuh);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                dbref.addSnapshotListener(maps.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(maps.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (documentSnapshot.exists()) {
                            if(!pkafrica.equals("Return Bike")) {
                                String bonucci = marker.getTitle();
                                String acmilan = documentSnapshot.getString(bonucci);
                                marker.setSnippet(acmilan);

                                switch (bonucci) {
                                    case "Africa":
                                        pkafrica = acmilan;
                                        break;
                                    case "CEDAT":
                                        pkcedat = acmilan;
                                        break;
                                    case "Complex":
                                        pkcomplex = acmilan;
                                        break;
                                    case "FEMA":
                                        pkfema = acmilan;
                                        break;
                                    case "Library":
                                        pklibrary = acmilan;
                                        break;
                                    case "Livingstone":
                                        pklivingstone = acmilan;
                                        break;
                                    case "Lumumba":
                                        pklumumba = acmilan;
                                        break;
                                    case "Main Gate":
                                        pkmaingate = acmilan;
                                        break;
                                    case "Marystuart":
                                        pkmarystuart = acmilan;
                                        break;
                                    case "Mitchell":
                                        pkmitchell = acmilan;
                                        break;
                                    case "Nkrumah":
                                        pknkrumah = acmilan;
                                        break;
                                    case "University Hall":
                                        pkuh = acmilan;
                                        break;
                                }
                            }
                        }
                    }
                });
                return false;
            }
        });
        int gg;

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
//                Intent intent = new Intent(Mapsimport1.this, Price.class);
//                intent.putExtra("marker", marker.getTitle());
//                startActivity(intent);

                String selected=marker.getTitle();

                switch (selected){
                    case "Africa":
                        if (pkafrica.equals("0")){

                        }else if (pkafrica.equals("Return Bike")){
                            availableBikes=2;
                        }else {
                            availableBikes=1;
                        }
                        ustation="Africa";
                        ustationcode="1";
                        break;
                    case "CEDAT":
                        if (pkcedat.equals("0")){}else if (pkcedat.equals("Return Bike")){availableBikes=2;}else {availableBikes=1;}
                        ustation="CEDAT";
                        ustationcode="2";
                        break;
                    case "Complex":
                        if (pkcomplex.equals("0")){}else if (pkcomplex.equals("Return Bike")){availableBikes=2;}else {availableBikes=1;}
                        ustation="Complex";
                        ustationcode="3";
                        break;
                    case "FEMA":
                        if (pkfema.equals("0")){}else if (pkfema.equals("Return Bike")){availableBikes=2;}else {availableBikes=1;}
                        ustation="FEMA";
                        ustationcode="4";
                        break;
                    case "Library":
                        if (pklibrary.equals("0")){}else if (pklibrary.equals("Return Bike")){availableBikes=2;}else {availableBikes=1;}
                        ustation="Library";
                        ustationcode="5";
                        break;
                    case "Livingstone":
                        if (pklivingstone.equals("0")){}else if (pklivingstone.equals("Return Bike")){availableBikes=2;}else {availableBikes=1;}
                        ustation="Livingstone";
                        ustationcode="6";
                        break;
                    case "Lumumba":
                        if (pklumumba.equals("0")){}else if (pklumumba.equals("Return Bike")){availableBikes=2;}else {availableBikes=1;}
                        ustation="Lumumba";
                        ustationcode="7";
                        break;
                    case "Main Gate":
                        if (pkmaingate.equals("0")){}else if (pkmaingate.equals("Return Bike")){availableBikes=2;}else {availableBikes=1;}
                        ustation="Main Gate";
                        ustationcode="8";
                        break;
                    case "Marystuart":
                        if (pkmarystuart.equals("0")){}else if (pkmarystuart.equals("Return Bike")){availableBikes=2;}else {availableBikes=1;}
                        ustation="Marystuart";
                        ustationcode="9";
                        break;
                    case "Mitchell":
                        if (pkmitchell.equals("0")){}else if (pkmitchell.equals("Return Bike")){availableBikes=2;}else {availableBikes=1;}
                        ustation="Mitchell";
                        ustationcode="10";
                        break;
                    case "Nkrumah":
                        if (pknkrumah.equals("0")){}else if (pknkrumah.equals("Return Bike")){availableBikes=2;}else {availableBikes=1;}
                        ustation="Nkrumah";
                        ustationcode="11";
                        break;
                    case "University Hall":
                        if (pkuh.equals("0")){}else if (pkuh.equals("Return Bike")){availableBikes=2;}else {availableBikes=1;}
                        ustation="University Hall";
                        ustationcode="12";
                        break;
                }

                if(availableBikes==1) {
                    showPopup();
                }else if(availableBikes==2){
//-REQUEST BIKE RETURN
                    Toast.makeText(getApplicationContext(), "Requesting Agent", Toast.LENGTH_LONG).show();
//                    returnBike(uagcode);

                    String today=getCurrentDate();
                    Map<String, Object> dataFour = new HashMap<>();
                    dataFour.put("phone_number", uphone);
                    dataFour.put("payment_method", paymentInt);
                    dataFour.put("email", umail);
                    dataFour.put("surname", usname);
                    dataFour.put("firstname", ufname);
                    dataFour.put("residence", uresi);
                    dataFour.put("station", ustation);
                    dataFour.put("digital_time", ditime);

                    db.collection("mukreturnrequests").document(uphone)
                            .set(dataFour)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("luanda", "Document successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("luanda", "Error writing document", e);
                                }
                            });

                    db.collection("mukreturnrequests").document(uphone)
                            .addSnapshotListener(maps.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                            if (e != null) {
                                Toast.makeText(maps.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (documentSnapshot.exists()) {
                                String yes="yes";
                                if(yes.equals(documentSnapshot.getString("has_rented"))){
                                Toast.makeText(maps.this, "bike returned thank you", Toast.LENGTH_SHORT).show();

                                    Intent int111=new Intent(maps.this,maps.class);
//        int111.putExtra("bikesin",ups);
                                    startActivity(int111);
                            }
                            }
                        }
                    });
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
////                db.collection("mukreturnrequests").document(umail)
////                        .delete()
////                        .addOnSuccessListener(new OnSuccessListener<Void>() {
////                            @Override
////                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(getApplicationContext(), "No response, please try again", Toast.LENGTH_SHORT).show();
////                            }
////                        })
////                        .addOnFailureListener(new OnFailureListener() {
////                            @Override
////                            public void onFailure(@NonNull Exception e) {
////
////                            }
////                        });
//
//                        }
//                    }, 20000);

                }else {
                    Toast.makeText(getApplicationContext(), "no bikes available at "+selected, Toast.LENGTH_SHORT).show();}
            }
        });

//        ApplicationService applicationService = new ApplicationService(Mapsimport1.this);
//        final Location newlocation = applicationService.getLocation(LocationManager.NETWORK_PROVIDER);
//        if (newlocation != null && isNetworkConnected()) {
//
//            double latitude = newlocation.getLatitude();
//            double longitude = newlocation.getLongitude();
//            final LatLng latLng = new LatLng(latitude, longitude);
//            if (mCurrLocationMarker != null) {
//                mCurrLocationMarker.setPosition(latLng);
//            } else {
//
//            }



//            Button findparkspot = findViewById(R.id.findbikepoint);
//            findparkspot.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    try {
//                        float mindist = 0;
//                        int pos = 0;
//                        for (int i = 0; i < markers.size(); i++) {
//                            Double slatlng1 = markers.get(i).getPosition().latitude;
//                            Double slatlng2 = markers.get(i).getPosition().longitude;
//                            LatLng lat = new LatLng(slatlng1, slatlng2);
//
//                            float[] distance = new float[1];
//
//                            Location.distanceBetween(newlocation.getLatitude(), newlocation.getLongitude(), slatlng1, slatlng2, distance);
//                            if (i == 0)
//                                mindist = distance[0];
//                            else if (mindist > distance[0]) {
//                                mindist = distance[0];
//                                pos = i;
//                            }
//                        }
//
//                        Toast.makeText(Mapsimport1.this, "Closest Parking Spot: " + markers.get(pos).getTitle(), Toast.LENGTH_LONG).show();
//                        CameraPosition myPosition = new CameraPosition.Builder()
//                                .target(markers.get(pos).getPosition()).zoom(14).bearing(90).tilt(30).build();
//                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition));
//
//                        String url = getDirectionsUrl(latLng, markers.get(pos).getPosition());
//                        DownloadTask downloadTask = new DownloadTask();
//
//                        // Start downloading json data from Google Directions API
//                        downloadTask.execute(url);
//
//                    } catch (Exception e) {
//
//                    }
//                }
//            });
//        }

    }
    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        return null;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {

    }
    public void showInfoWindow(final LatLng position, final String titler,String snip){

        MarkerOptions cd = new MarkerOptions().position(position).title(titler).snippet(snip)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconnew3));
        switch (snip){
            case "0":
                mMap.addMarker(cd);
                break;
            default:
                if (location.equals("MUK")&&
                        !(snip.equals("0"))){
                    mMap.addMarker(cd).showInfoWindow();
                }
                break;
        }
        markers.add(cd);
    }
    public void showPopup() {
        ImageView closePopup;
        closePopup =(ImageView) myDialog.findViewById(R.id.close_popup);
        closePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        RadioButton radi=(RadioButton)myDialog.findViewById(R.id.radio_digitime12);
        radi.setText("Digital Time("+ditime+" mins)");

        durationtext =(TextView)myDialog.findViewById(R.id.duration1);
        seekduration =(SeekBar)myDialog.findViewById(R.id.seekbar1);

        seekduration.setMax(max-min);
        seekduration.setProgress(current-min);
//        duration="20 minutes @ 500";
//        durationtext.setText(duration);

        seekduration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                current=progress+min;

                switch (current){
                    case 0:
                        udura="20";
                        duration="20 minutes @ 500";
                        prx="500";
                        durationtext.setText(duration);
                        break;
                    case 1:
                        udura="1";
                        duration="1 hour @ 1000";
                        prx="1000";
                        durationtext.setText(duration);
                        break;
                    case 2:
                        udura="2";
                        duration="2 hours @ 2000";
                        prx="2000";
                        durationtext.setText(duration);
                        break;
                    case 3:
                        udura="3";
                        duration="3 hours @ 3000";
                        prx="3000";
                        durationtext.setText(duration);
                        break;
//                    case 4:
//                        udura="4";
//                        duration="4 hours @ 4000";
//                        durationtext.setText(duration);
//                        break;
//                    case 5:
//                        udura="5";
//                        duration="5 hours @ 5000";
//                        durationtext.setText(duration);
//                        break;
                    case 4:
                        udura="6";
                        duration="Half day @ 5000";
                        prx="5000";
                        durationtext.setText(duration);
                        break;
                    case 5:
                        udura="12";
                        duration="Full day @ 10000";
                        prx="10000";
                        durationtext.setText(duration);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        myDialog.show();
    }
    public String prices(String time){
        String cash="0";
        switch (time) {
            case "20":
                cash="500";
                break;
            case "1":
                cash="1000";
                break;
            case "2":
                cash="2000";
                break;
            case "3":
                cash="3000";
                break;
            case "6":
                cash="5000";
                break;
            case "12":
                cash="10000";
                break;
        }
        return cash;
    }
    public String times(String time){
        String cash="0";
        switch (time) {
            case "20":
                cash="20 mins";
                break;
            case "1":
                cash="1 hr";
                break;
            case "2":
                cash="2 hrs";
                break;
            case "3":
                cash="3 hrs";
                break;
            case "6":
                cash="5 hrs";
                break;
            case "12":
                cash="10 hrs";
                break;
        }
        return cash;
    }
    public  void goToPrice(View view){
        Button bproc=myDialog.findViewById(R.id.go_to_pickup_button);
//        ANIMATION
        Animation animation= AnimationUtils.loadAnimation(maps.this,R.anim.bounce);
        bproc.startAnimation(animation);

        String money=prices(udura);
        String timestring=times(udura);
        String today=getCurrentDate();
        Map<String, Object> dataOne = new HashMap<>();
        dataOne.put("phone_number", uphone);
        dataOne.put("duration", timestring);
        dataOne.put("payment_method", paymentInt);
        dataOne.put("email", umail);
        dataOne.put("amount", money);
        dataOne.put("surname", usname);
        dataOne.put("firstname", ufname);
        dataOne.put("residence", uresi);
        dataOne.put("station", ustation);
        dataOne.put("digital_time", ditime);

        if (pmc==0) {
            //REQUEST DIGITAL TIME FROME HERE
            if(ditime.equals("0")&& pmc>0){
                Toast.makeText(getApplicationContext(), "You do not have digital time to spend", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "requesting.......", Toast.LENGTH_LONG).show();
                Log.d("JSONStatus", "requestING");

                db.collection("mukcurrentrequests").document(uphone)
                        .set(dataOne)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
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
        }else if (pmc>0){
            db.collection("mukcurrentrequests").document(uphone)
                    .set(dataOne)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("luanda", "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("luanda", "Error writing document", e);
                        }
                    });
        }else {
            Toast.makeText(getApplicationContext(), "check the buttons...", Toast.LENGTH_LONG).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                db.collection("mukcurrentrequests").document(umail)
//                        .delete()
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(getApplicationContext(), "No response, please try again", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                            }
//                        });

            }
        }, 20000);
    }
    public void onRadioButtonClickedPayment(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_cash12:
                if (checked){
//                    payment="cash";
                    paymentInt="cash";
                    checkPm++;
                    pmc++;
                    pmd=0;
                    Log.d("checkStatus", "cash: "+pmc+"dtime: "+pmd);
                }
                break;
            case R.id.radio_digitime12:
                if (checked){
//                    payment="DT";
                    paymentInt="DT";
                    checkPm++;
                    pmd++;
                    pmc=0;
                    Log.d("checkStatus", "cash: "+pmc+"dtime: "+pmd);

//                    Toast.makeText(getApplicationContext(), "Not yet available Coming soon", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }

}