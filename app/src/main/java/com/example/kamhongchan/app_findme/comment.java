package com.example.kamhongchan.app_findme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class comment extends AppCompatActivity {
    private static final String TAG = "comment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = (Button) findViewById(R.id.bt_submitcomment);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date dt=new Date();
                String time=sdf.format(dt);

                EditText et_comment = (EditText) findViewById(R.id.et_comment);
                EditText et_phone = (EditText) findViewById(R.id.et_phone);
                EditText et_email = (EditText) findViewById(R.id.et_email);

                String content = et_comment.getText().toString();
                String phone = et_phone.getText().toString();
                String email = et_email.getText().toString();

                // Access a Cloud Firestore instance from your Activity
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> comment = new HashMap<>();
                comment.put("時間", time);
                comment.put("電話", phone);
                comment.put("電子郵箱",email);
                comment.put("內容", content);


                // Add a new document with a generated ID
                db.collection("comment")
                        .add(comment)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

                Toast toast = Toast.makeText(comment.this, "我們已收到你的意見", Toast.LENGTH_LONG);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intentback = new Intent(comment.this,MapsActivity.class);
                startActivity(intentback);
            }
    });
}}
