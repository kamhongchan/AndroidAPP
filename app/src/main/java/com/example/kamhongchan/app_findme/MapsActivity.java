package com.example.kamhongchan.app_findme;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int REQUEST_LOCATION =2;
    private Button btdemo;
    private Button btphone;
    private static final String TAG = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button btmenu = (Button)findViewById(R.id.btmenu);
        btmenu.getBackground().setAlpha(200);//0~255透明度值
        btmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentmenu = new Intent();
                intentmenu.setClass(MapsActivity.this  , menu.class);
                startActivity(intentmenu);
                Toast.makeText(MapsActivity.this, "選單", Toast.LENGTH_LONG).show();
            }
        });

        FloatingActionButton fab_phone= (FloatingActionButton)findViewById(R.id.fab_phone);
        fab_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MapsActivity.this  , contact.class);
                startActivity(intent);
                Toast.makeText(MapsActivity.this, "電話簿", Toast.LENGTH_LONG).show();
            }
        });

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
        //mMap.getUiSettings().setZoomControlsEnabled(true);  // 右下角的放大縮小功能
        mMap.getUiSettings().setCompassEnabled(true);       // 左上角的指南針，要兩指旋轉才會出現

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                     new String[] {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION
            );
        }
        else{mMap.setMyLocationEnabled(true);}

        // Add a marker in Keelung Location and move the camera
        LatLng home = new LatLng(25.137187, 121.793778);
        mMap.addMarker(new MarkerOptions().position(home).title("基隆市山海關公寓大廈"));

        LatLng keelungcity = new LatLng(25.131465, 121.744551);
        mMap.addMarker(new MarkerOptions().position(keelungcity).title("基隆市政府"));

        //切換Camera到基隆地區
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        //LatLng KeelungArea = new LatLng(25.128219, 121.739188);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KeelungArea,15.0f));
       // String text="00457056";

        
        //利用Toast的靜態函式makeText來建立Toast物件(測試字句)
        //Toast toast = Toast.makeText(MapsActivity.this, "開始定位"+text, Toast.LENGTH_LONG);
        //顯示Toast
        //toast.show();

        //獲取經緯度並切換Camera到現時位置
        LocationManager lms = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location location = lms.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lms.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Double longitude = location.getLongitude();
        Double latitude = location.getLatitude();
        Toast.makeText(this, "Lo = " + longitude + "; La = " + latitude ,Toast.LENGTH_SHORT).show();

        LatLng nowArea = new LatLng(latitude,longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( nowArea,15.0f));

        //上傳資料到Firebase
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date dt=new Date();
        String date=sdf.format(dt);
        String time=sdf1.format(dt);
        String ID="HTC Desire 650";
        String slatitude = latitude.toString();
        String slongitude = longitude.toString();

        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> users = new HashMap<>();
        users.put("name", "HTC Desire 650");
        users.put("time", time);
        users.put("latitude", slatitude);
        users.put("longitude", slongitude);
        users.put("linkmap","http://maps.google.com/?q="+slatitude+","+slongitude);


        //Set document & sub-class
        String dclass=ID+"/"+date;

        // Add a new document with a generated ID
        db.collection("users").document(ID).collection(date).add(users);
        db.collection("users").document(ID).set(users);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int [] grantResults){
        switch (requestCode) {
            case REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //使用者允許權限
                    mMap.setMyLocationEnabled(true);
                } else
                {
                    //使用者拒絕受權,停用MyLocation Function
                }
                break;
        }
    }
}
