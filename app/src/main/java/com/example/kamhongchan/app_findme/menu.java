package com.example.kamhongchan.app_findme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Setting");
        Button btexit = (Button)findViewById(R.id.bt_exit);
        btexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(menu.this, "退出Find Me", Toast.LENGTH_LONG).show();
            }
        });

        Button btcomment = (Button)findViewById(R.id.bt_comment);
        btcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcomment = new Intent();
                intentcomment.setClass(menu.this  , comment.class);
                startActivity(intentcomment);
                Toast.makeText(menu.this, "反映與建議", Toast.LENGTH_LONG).show();
            }
        });

        Button btpolicy = (Button)findViewById(R.id.bt_policy);
        btpolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentpolicy = new Intent();
                intentpolicy.setClass(menu.this  , PrivacyPolicy.class);
                startActivity(intentpolicy);
                Toast.makeText(menu.this, "私隱權保護政策", Toast.LENGTH_LONG).show();
            }
        });
    }
}
