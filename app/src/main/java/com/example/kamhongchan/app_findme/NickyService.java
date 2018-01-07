package com.example.kamhongchan.app_findme;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//繼承android.app.Service
public class NickyService extends Service {
    private Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        handler.postDelayed(showTime, 1000);
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(showTime);
        super.onDestroy();
    }

    private Runnable showTime = new Runnable() {
        public void run() {
            //log目前時間
            Log.i("time:", new Date().toString());
            LocationManager lms = (LocationManager) getSystemService(LOCATION_SERVICE);
            Location location = lms.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lms.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Double longitude = location.getLongitude();
            Double latitude = location.getLatitude();

            LatLng nowArea = new LatLng(latitude,longitude);
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
            users.put("!linkmap","http://maps.google.com/?q="+slatitude+","+slongitude);


            //Set document & sub-class
            String dclass=ID+"/"+date;

            // Add a new document with a generated ID
            db.collection("users").document(ID).collection(date).add(users);
            db.collection("users").document(ID).set(users);
            handler.postDelayed(this, 600000);
        }
    };
}
