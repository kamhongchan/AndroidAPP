package com.example.kamhongchan.app_findme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class PrivacyPolicy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        setTitle("Privacy Policy");
        TextView tvpolicy = (TextView)findViewById(R.id.tv_policy);
        tvpolicy.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
